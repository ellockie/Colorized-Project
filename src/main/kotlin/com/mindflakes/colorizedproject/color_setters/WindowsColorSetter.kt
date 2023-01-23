package com.mindflakes.colorizedproject.color_setters

import com.mindflakes.colorizedproject.utils.getForegroundColorBasedOnBrightness
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.impl.IdeMenuBar
import java.awt.Color
import java.awt.Container

class WindowsColorSetter : ColorSetter() {

    override val title_bar_component_path = listOf("JBLayeredPane", "MenuFrameHeader")

    override fun setTitleBar(color: Color, project: Project) {
        val titleBarComponent: Container = findTitleBarComponent(project)
        lockComponentColorProperty(project, titleBarComponent, "both", true)
        lockContainerColor(findIdeMenuBar(titleBarComponent), project)

        // set the color
        titleBarComponent.background = color
        recursiveSetComponentColor(titleBarComponent, getForegroundColorBasedOnBrightness(color), "foreground")
    }

    private fun findIdeMenuBar(titleBarComponent: Container): IdeMenuBar {
        for (comp in titleBarComponent.components) {
            if ("JPanel" in comp.toString()) {
                for (subComp in (comp as Container).components) {
                    if ("IdeMenuBar" in subComp.toString()) {
                        return subComp as IdeMenuBar
                    }
                }
            }
        }
        throw ClassNotFoundException()
    }
}