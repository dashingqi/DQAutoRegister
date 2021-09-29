package com.dashingqi.android.register

import java.util.regex.Pattern

/**
 * 用户手动配置的信息实体类
 */
class AutoRegisterInfo {
    /** 需要扫描的接口*/
    String interfaceName = ''
    /** 代码注入的类名*/
    String insertClassName = ''
    /** 代码注入的方法名*/
    String insertMethodName = ''
    /** 注册方法所在的类*/
    String registerClassName = ''
    /** 生成的代码所调用的方法*/
    String registerMethodName = ''
    ArrayList<String> include = []

    ArrayList<Pattern> includePatterns = []
    File fileContainsInitClass

    /**
     * 数据初始化操作
     */
    void init() {
        if (include == null) {
            include = new ArrayList<String>()
        }
        if (include.isEmpty()) {
            include.add(".*")
        }

        if (!registerClassName) {
            registerClassName = insertClassName
        }

        if (insertClassName) {
            insertClassName = convertDotToSlash(insertClassName)
        }

        if (interfaceName) {
            interfaceName = convertDotToSlash(interfaceName)
        }

        initPattern(include, includePatterns)
    }

    private static void initPattern(ArrayList<String> list, ArrayList<Pattern> patterns) {
        list.each { s ->
            patterns.add(Pattern.compile(s))
        }
    }

    /**
     * 将 . 装换成 /
     * @param str
     * @return
     */
    private static String convertDotToSlash(String str) {
        return str ? str.replaceAll('\\.', '/').intern() : str
    }

    @Override
    String toString() {
        return "insterfaceName = $interfaceName , " +
                "insertClassName = $insertClassName , " +
                "insertMethodName = $insertMethodName , " +
                "registerClassName = $registerClassName , " +
                "registerMethodName = $registerMethodName"
    }
}