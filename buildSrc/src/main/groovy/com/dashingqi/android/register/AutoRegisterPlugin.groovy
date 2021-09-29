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
    public static final String EXTENSION_NAME = "autoRegister"

    @Override
    void apply(Project project) {
        // Application 插件
        if (project.plugins.hasPlugin(AppPlugin)) {
            // 注册自定义的Transform
            def appExtensions = project.extensions.getByType(AppExtension)
            AutoRegisterTransform autoRegisterTransform = new AutoRegisterTransform(project)
            appExtensions.registerTransform(autoRegisterTransform)
            project.getExtensions().create(EXTENSION_NAME, AutoRegister)
            project.afterEvaluate {
                // 这个方法是要早于transform方法执行
                convert(project, autoRegisterTransform)
            }
        }
    }

    /**
     * 用于做数据的转换
     * @param project
     */
    static void convert(Project project, AutoRegisterTransform transform) {
        AutoRegister extension = project[EXTENSION_NAME]
        extension.project = project
        extension.convert()
        transform.mAutoRegister = extension
        println "size = ${extension.registerInfo.size()}"
        println "list size = ${extension.list.size()}"
    }
}