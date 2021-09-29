package com.dashingqi.android.register

import org.gradle.api.Project

/**
 * 配置实体类
 */
class AutoRegister {
    /** 用于收集需要注册的信息*/
    ArrayList<Map<String, Object>> registerInfo = []
    /** project 实例*/
    Project project
    /** 用于收集注册信息的实体*/
    ArrayList<AutoRegisterInfo> list = new ArrayList<>()

    /** 扫描接口的名称*/
    final String SCAN_INTERFACE_NAME = "scanInterfaceName"
    /** 代码注入的类名*/
    final String CODE_INSERT_TO_CLASS_NAME = "codeInsertToClassName"
    /** 代码注入的方法名称*/
    final String CODE_INSET_TO_METHOD_NAME = "codeInsertToMethodName"
    /** 注册的类名称*/
    final String REGISTER_CLASS_NAME = "registerClassName"
    /** 注册的方法名称*/
    final String REGISTER_METHOD_NAME = "registerMethodName"

    /** 空的构造方法*/
    AutoRegister() {}

    /**
     * 数据转换
     */
    void convert() {
        // 将闭包中的数据转换成list中
        registerInfo.forEach { it ->
            // 实例化一个AutoRegisterInfo
            AutoRegisterInfo autoRegisterInfo = new AutoRegisterInfo()
            autoRegisterInfo.interfaceName = it.get(SCAN_INTERFACE_NAME)
            autoRegisterInfo.insertClassName = it.get(CODE_INSERT_TO_CLASS_NAME)
            autoRegisterInfo.insertMethodName = it.get(CODE_INSET_TO_METHOD_NAME)
            autoRegisterInfo.registerClassName = it.get(REGISTER_CLASS_NAME)
            autoRegisterInfo.registerMethodName = it.get(REGISTER_METHOD_NAME)
            // 存储这个AutoRegisterInfo实例
            list.add(autoRegisterInfo)
            // 初始化数据
            autoRegisterInfo.init()
        }
        print list
    }
}