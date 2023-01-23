package com.mindflakes.colorizedproject

import com.mindflakes.colorizedproject.color_setters.ColorSetterFactory
import com.mindflakes.colorizedproject.ui.AUTO_COLOR_SET_TOGGLED_PATH
import com.mindflakes.colorizedproject.utils.getColorBasedOnProjectName
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.project.Project
import java.awt.Color
import java.awt.Component

const val COLOR_SETTING_PATH = "com.mindflakes.colorizedproject.rgb"
const val OVERRIDE_AUTO_COLOR_CONFIG = "com.mindflakes.colorizedproject.override_auto_rgb"
val gColorLockedComponentMap = mutableMapOf<Component, Boolean>()
val gProjectLockedComponentsMap = mutableMapOf<Project, List<Component>>()
val gProjectColorMap = mutableMapOf<Project, Color>()
val gProjectColorLockedMap = mutableMapOf<Project, Boolean>()

/**
 * Set title bar color with given color for given project.
 */
fun setTitleBarColor(color: Color, project: Project) {

    gProjectColorMap[project] = color
    ColorSetterFactory.getColorSetter().setTitleBar(color, project)

    if(PropertiesComponent.getInstance().getBoolean(AUTO_COLOR_SET_TOGGLED_PATH)
        && color != getColorBasedOnProjectName(project)
    ){
        PropertiesComponent.getInstance(project).setValue(OVERRIDE_AUTO_COLOR_CONFIG, true)
    }
    // save color config for persistence
    PropertiesComponent.getInstance(project).setValue(COLOR_SETTING_PATH, color.rgb, 0)
}
