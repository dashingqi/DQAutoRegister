package com.dashingqi.android.register

import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * AutoRegisterPlugin
 */
class AutoRegisterPlugin implements Plugin<Project> {

    /** extension name */
    private String EXTENSION_NAME = "autoregister"

    @Override
    void apply(Project project) {
        // Application 插件
        if (project.plugins.hashPlugin(AppPlugin)) {
            // 注册自定义的Transform
            def appExtensions = project.extensions.getByType(AppPlugin)
            AutoRegisterTransform autoRegisterTransform = new AutoRegisterTransform()
            appExtensions.registerTransform(autoRegisterTransform)
            project.getExtensions().create(EXTENSION_NAME, AutoRegister)

            project.afterEvaluate {
                AutoRegister autoRegister = project[EXTENSION_NAME]
                println("size = ${autoRegister.registerInfo.size()}")
            }
        }
    }
}