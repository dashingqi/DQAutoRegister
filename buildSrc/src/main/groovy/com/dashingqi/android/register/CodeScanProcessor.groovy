package com.dashingqi.android.register

/**
 * 代码扫描处理器
 */
class CodeScanProcessor {

    /** 注册的实体类集合*/
    ArrayList<AutoRegisterInfo> mRegisterInfoList

    CodeScanProcessor(ArrayList<AutoRegisterInfo> registerInfoList) {
        mRegisterInfoList = registerInfoList
    }


    boolean checkClass(String entryName, File destFile) {
        if (entryName == null || !entryName.endsWith(".class")) {
            return false
        }
        entryName = entryName.substring(0, entryName.lastIndexOf("."))
        println "entryName Processor = $entryName"
        def found = false
        mRegisterInfoList.each {
            if (it.insertClassName == entryName) {
                println "insertClassName = ${it.insertClassName}"
                it.fileContainsInitClass = destFile
                println "destFile name = ${destFile.name}"
                if (destFile.name.endsWith(".jar")) {
                    found = true
                }
            }
        }
        return found
    }

    /**
     * 是否应该加工Class
     * @param entryName
     * @return
     */
    boolean shouldProcessClass(String entryName) {
        if (entryName == null || !entryName.endsWith(".class")) {
            return false
        }
        entryName = entryName.substring(0, entryName.lastIndexOf("."))
        def length = mRegisterInfoList.size()
        for (int i = 0; i < length; i++) {
            if (shouldProcessThisClassForRegister(mRegisterInfoList[i], entryName)) {
                return true
            }
        }
        return false
    }

    /**
     * 进行过滤器的过滤
     * @return
     */
    private static boolean shouldProcessThisClassForRegister(AutoRegisterInfo info, String entryName) {
        return false
    }
}