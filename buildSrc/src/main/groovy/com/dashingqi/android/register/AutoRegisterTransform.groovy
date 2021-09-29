package com.dashingqi.android.register

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.gradle.api.Project

/**
 * 自定义的Transform
 */
class AutoRegisterTransform extends Transform {

    /** AutoRegister 实例*/
    AutoRegister mAutoRegister
    /** Project 实例*/
    Project mProject

    AutoRegisterTransform(Project project) {
        mProject = project
    }

    @Override
    String getName() {
        return "AutoRegisterTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否支持增量编译
     * @return
     */
    @Override
    boolean isIncremental() {
        return false
    }

/**
 * 1. 遍历所有Input
 * 2. 对Input进行二次处理
 * 3. 拷贝Input到目标目录
 * @param transformInvocation
 * @throws TransformException* @throws InterruptedException* @throws IOException
 */
    @Override
    void transform(TransformInvocation transformInvocation)
            throws TransformException, InterruptedException, IOException {

        boolean leftSlash = File.separator == '/'
        CodeScanProcessor codeScanProcessor = new CodeScanProcessor(mAutoRegister.list)
        transformInvocation.inputs.each { transformInput ->
            transformInput.directoryInputs.each { directoryInput ->
                def directoryFile = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes,
                        directoryInput.scopes,
                        Format.DIRECTORY)
                String rootPath = directoryInput.file.absolutePath
                if (!rootPath.endsWith(File.separator)) {
                    rootPath += File.separator
                }
                directoryInput.file.eachFileRecurse { file ->
                    def path = file.absolutePath.replace(rootPath, '')
                    println "path = $path"
                    if (file.isFile()) {
                        def entryName = path
                        if (!leftSlash) {
                            entryName = entryName.replaceAll("\\\\", "/")
                        }
                        println "entryName  = $entryName"
                        codeScanProcessor.checkClass(entryName,
                                new File(directoryFile.absolutePath + File.separator + path))

                    }
                }


                FileUtils.copyDirectory(directoryInput.file, directoryFile)
            }

            transformInput.jarInputs.each { jarInput ->
                def jarFile = transformInvocation.outputProvider.getContentLocation(jarInput.name,
                        jarInput.contentTypes,
                        jarInput.scopes,
                        Format.JAR)
                FileUtils.copyFile(jarInput.file, jarFile)
            }
        }
    }
}