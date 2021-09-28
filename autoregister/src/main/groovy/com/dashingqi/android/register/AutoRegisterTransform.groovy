package com.dashingqi.android.register

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform

/**
 * 自定义的Transform
 */
class AutoRegisterTransform extends Transform {

    @Override
    String getName() {
        return null
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return null
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return null
    }

    @Override
    boolean isIncremental() {
        return false
    }
}