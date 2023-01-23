package com.mindflakes.colorizedproject.color_setters

import com.mindflakes.colorizedproject.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.ColorUtil
import java.awt.Color
import java.awt.Component
import java.awt.Container
import java.awt.event.ContainerEvent
import java.awt.event.ContainerListener
import java.security.InvalidParameterException
import javax.swing.JFrame

abstract class ColorSetter {

    // Title bar's component path down from the main ide component (differs between operating systems)
    abstract val TITLE_BAR_COMPONENT_PATH: List<String>

    /**
     * Set title bar color!
     *
     * @param color the color to set to.
     * @param project the relevant project to set the title bar color to,
     *        since different projects have separate windows.
     */
    abstract fun setTitleBar(color: Color, project: Project)

    /**
     * Additional cleanup on project close (if necessary).
     */
    open fun cleanUp(project: Project) {
    }

    /**
     * Retrieve the title bar component for given project.
     */
    open fun findTitleBarComponent(
        project: Project,
        path: List<String> = TITLE_BAR_COMPONENT_PATH
    ): Container {
        val mainIdeComponent = (WindowManager.getInstance().getFrame(project) as JFrame).getComponent(0) as Container
        return findComponent(mainIdeComponent, path) as Container
    }

    /**
     * Find subcomponent from a root, given a path to search for.
     *
     * @param root the component from which to start searching.
     * @param path a list of component names to search for, by order.
     *              for example, given the path ["sub1","sub2","sub3"],
     *              root param will be searched for sub1, then if found,
     *              sub1 will be searched for sub2, and so on.
     * @return the final component in the given path param (e.g "sub3" would be returned
     *          from the example above) if found.
     * @throws ClassNotFoundException if anywhere down the component tree a component from
     *         path param is not found.
     */
    protected fun findComponent(root: Container, path: List<String>): Component {
        var currentComponent = root
        var found = false

        for (componentName in path) {
            for (component in currentComponent.components) {
                if (componentName in component.toString()) {
                    currentComponent = component as Container
                    found = true
                    break
                }
            }

            if (!found) throw ClassNotFoundException()
            found = false
        }

        return currentComponent
    }

    /**
     * Set a given property's color recursively for given component.
     *
     * @param container the root component (as container)
     * @param color color to set the property to
     * @param property property to repaint
     */
    protected fun recursiveSetComponentColor(container: Container, color: Color, property: String) {

        when (property) {
            "background" -> container.background = color
            "foreground" -> container.foreground = color
            else -> throw InvalidParameterException("Unsupported property given!")
        }
        for (comp in container.components) {
            recursiveSetComponentColor(comp as Container, color, property)
        }
    }

    /**
     * Disable any unintended changes to given component of a project from external sources.
     *
     * @param component component to lock changes to.
     * @param project the project to which the component belongs to.
     * @param property the property which we want to lock.
     *
     * @note registers a PropertyChangeListener for given property name which `aggressively`
     * reverts the color back to the one set by this plugin.
     */
    protected fun lockComponentColorProperty(
        project: Project,
        component: Component,
        property: String = "both",
        recursive: Boolean = false
    ) {
        if (!gColorLockedComponentMap.containsKey(component)) {
            gColorLockedComponentMap[component] = false
        }

        if (gColorLockedComponentMap[component] == false) {
            if (property == "both") {
                addPropertyChangeListener(project, component, "foreground")
                addPropertyChangeListener(project, component, "background")
            } else {
                addPropertyChangeListener(project, component, property)
            }
            gColorLockedComponentMap[component] = true
        }

        if (project in gProjectLockedComponentsMap) {
            gProjectLockedComponentsMap[project]?.plus(component)
        } else {
            gProjectLockedComponentsMap[project] = mutableListOf(component)
        }

        if (recursive) {
            for (subComponent in (component as Container).components) {
                lockComponentColorProperty(project, subComponent, property, true)
            }
        }
    }

    private fun addPropertyChangeListener(project: Project, component: Component, property: String) {
        component.addPropertyChangeListener(property) {
            if (project in gProjectColorMap && it.newValue != (gProjectColorMap[project] as Color).rgb) {
                val color = gProjectColorMap[project] as Color
                when (property) {
                    "background" -> component.background = color
                    "foreground" -> component.foreground = if (ColorUtil.isDark(color)) Color.white else Color.black
                }
            }
        }
    }

    /**
     * Lock color properties for given container of components.
     *
     * @param container the container to lock changes to.
     * @param project the project to which the container belongs to.
     *
     * @note For some reason, Swing (or Intellij) completely swap out some container's components
     *       when rebuilding the UI, so the color locking trick I did earlier is not enough,
     *       since it will only lock components which might get disposed of some day...
     *       Hence, this function is introduced, which adds a listener to container change events
     *       such as component added, component removed, etc.
     *       Once such event occurs, the title bar color will be reset.
     */
    fun lockContainerColor(container: Container, project: Project) {
        if (!gProjectColorLockedMap.containsKey(project) || gProjectColorLockedMap[project] == false) {
            container.addContainerListener(object : ContainerListener {
                override fun componentAdded(e: ContainerEvent?) {
                    gProjectColorMap[project]?.let { setTitleBarColor(it, project) }
                }

                override fun componentRemoved(e: ContainerEvent?) {
                    try {
                        gProjectColorMap[project]?.let { setTitleBarColor(it, project) }
                    } catch (_: NullPointerException) {
                        // could happen if current project is closing, nothing to do about it
                    }
                }
            })
            gProjectColorLockedMap[project] = true
        }
    }
}