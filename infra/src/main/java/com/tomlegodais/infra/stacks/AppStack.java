package com.tomlegodais.infra.stacks;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.iam.*;
import software.amazon.awscdk.services.rds.*;
import software.amazon.awscdk.services.secretsmanager.Secret;
import software.amazon.awscdk.services.secretsmanager.SecretStringGenerator;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class AppStack extends Stack {

    private static final String DATABASE_NAME = "tl0823db";

    public AppStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);

        var vpc = Vpc.Builder.create(this, "tl0823-vpc")
                .maxAzs(2)
                .subnetConfiguration(List.of(
                        SubnetConfiguration.builder()
                                .subnetType(SubnetType.PUBLIC)
                                .cidrMask(24)
                                .name("tl0823-public-subnet")
                                .build()
                ))
                .build();

        var securityGroup = SecurityGroup.Builder.create(this, "tl0823-db-sg")
                .vpc(vpc)
                .allowAllOutbound(true)
                .build();

        var databaseSecrets = Secret.Builder.create(this, "tl0823-db-secrets")
                .secretName("tl0823-db-secrets")
                .generateSecretString(SecretStringGenerator.builder()
                        .secretStringTemplate("{\"username\":\"tl0823\"}")
                        .generateStringKey("password")
                        .excludePunctuation(true)
                        .passwordLength(30)
                        .build())
                .build();

        var databaseCluster = DatabaseCluster.Builder.create(this, "tl0823-db-cluster")
                .engine(DatabaseClusterEngine.auroraPostgres(AuroraPostgresClusterEngineProps.builder()
                        .version(AuroraPostgresEngineVersion.VER_13_9)
                        .build()))
                .credentials(Credentials.fromSecret(databaseSecrets))
                .writer(ClusterInstance.serverlessV2("tl0823-db-writer", ServerlessV2ClusterInstanceProps.builder()
                        .publiclyAccessible(true)
                        .build()))
                .defaultDatabaseName(DATABASE_NAME)
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        .build())
                .securityGroups(List.of(securityGroup))
                .build();

        var cluster = Cluster.Builder.create(this, "tl0823-ecs-cluster").build();
        var repository = Repository.fromRepositoryName(this, "tl0823-ecr", "tl0823-api");
        var fargateService = ApplicationLoadBalancedFargateService.Builder.create(this, "tl0823-alb-fargate-service")
                .serviceName("tl0823-api-service")
                .cluster(cluster)
                .taskImageOptions(ApplicationLoadBalancedTaskImageOptions.builder()
                        .image(ContainerImage.fromEcrRepository(repository))
                        .environment(Map.of(
                                "AURORA_SECRET_ARN", databaseSecrets.getSecretArn()
                        ))
                        .taskRole(this.createFargateTaskRole(databaseSecrets.getSecretArn()))
                        .build())
                .memoryLimitMiB(2048)
                .cpu(512)
                .desiredCount(1)
                .publicLoadBalancer(true)
                .build();

        fargateService.getTargetGroup().configureHealthCheck(new HealthCheck.Builder()
                .path("/actuator/health")
                .healthyHttpCodes("200")
                .interval(Duration.minutes(1))
                .timeout(Duration.seconds(10))
                .build());

        fargateService.getNode().addDependency(databaseCluster);
        securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(5432), "Allow postgres access from the world");
    }

    private Role createFargateTaskRole(String secretArn) {
        var document = PolicyDocument.Builder.create()
                .statements(List.of(
                        PolicyStatement.Builder.create()
                                .actions(List.of("secretsmanager:GetSecretValue"))
                                .resources(List.of(secretArn))
                                .effect(Effect.ALLOW)
                                .build()
                ))
                .build();

        return Role.Builder.create(this, "tl0823-fargate-task-role")
                .assumedBy(new ServicePrincipal("ecs-tasks.amazonaws.com"))
                .inlinePolicies(Map.of("tl0823-fargate-task-policy", document))
                .build();
    }
}
