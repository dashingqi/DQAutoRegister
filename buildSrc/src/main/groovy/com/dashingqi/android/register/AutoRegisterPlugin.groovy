package com.dashingqi.android.register

import com.android.build.gradle.AppExtension
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
        if (project.plugins.hasPlugin(AppPlugin)) {
            // 注册自定义的Transform
            def appExtensions = project.extensions.getByType(AppExtension)
            AutoRegisterTransform autoRegisterTransform = new AutoRegisterTransform()
            appExtensions.registerTransform(autoRegisterTransform)
            project.getExtensions().create(EXTENSION_NAME, AutoRegister)
        }

        project.afterEvaluate {
            AutoRegister extension = project[EXTENSION_NAME]
            if (extension != null) {
                println "size = ${extension.info}"
            }
        }
    }
}