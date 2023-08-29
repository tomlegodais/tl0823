package com.tomlegodais.app.repository;

import com.tomlegodais.shared.model.Tool;

import java.util.LinkedList;
import java.util.List;

import static com.tomlegodais.app.Constants.ToolBrands.*;
import static com.tomlegodais.app.Constants.ToolTypes.*;

public class DummyToolRepository {

    private static final List<Tool> TOOLS = new LinkedList<>();

    static {
        addTool(new Tool("CHNS", CHAINSAW, STIHL));
        addTool(new Tool("LADW", LADDER, WERNER));
        addTool(new Tool("JAKD", JACKHAMMER, DEWALT));
        addTool(new Tool("JAKR", JACKHAMMER, RIDGID));
    }

    private DummyToolRepository() {
        /* empty */
    }

    public static void addTool(Tool tool) {
        var existingTool = findByCode(tool.getCode());
        if (existingTool != null) {
            throw new IllegalArgumentException("Tool with code " + tool.getCode() + " already exists");
        }

        TOOLS.add(tool);
    }

    public static Tool findByCode(String code) {
        return TOOLS.stream()
                .filter(tool -> tool.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}