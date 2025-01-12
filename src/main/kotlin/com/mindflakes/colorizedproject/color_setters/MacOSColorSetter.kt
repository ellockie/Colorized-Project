package com.mindflakes.colorizedproject.color_setters

import com.mindflakes.colorizedproject.utils.getForegroundColorBasedOnBrightness
import com.intellij.openapi.project.Project
import java.awt.Color
import java.awt.Container

open class MacOSColorSetter : ColorSetter() {

    override val title_bar_component_path = listOf("JBLayeredPane", "JBPanel", "NonOpaquePanel", "JBBox")

    override fun setTitleBar(color: Color, project: Project) {

        val titleBarComponent = findTitleBarComponent(project)
        val filePathComponent = findFilePathComponent(titleBarComponent)

        recursiveSetComponentColor(titleBarComponent, color, "background")
        recursiveSetComponentColor(filePathComponent, getForegroundColorBasedOnBrightness(color), "foreground")
    }

    /**
     * Search for file path component that appears in the navbar,
     * which we need to change to foreground color of the text.
     *
     * @note this is used instead of findComponent because it has a different searching algorithm.
     */
    private fun findFilePathComponent(titleBarComponent: Container): Container {
        val navBarComponent = findComponent(titleBarComponent,
            listOf(
                "MyNavBarWrapperPanel",
            )
        ) as Container
        for (component in navBarComponent.components) {
            for (subComponent in (component as Container).components) {
                if ("JBScrollPane" in subComponent.toString()) {
                    return component
                }
            }
        }

        throw ClassNotFoundException()
    }
}
