package com.cherry.lib.luban.io

import java.io.IOException
import java.io.InputStream
import kotlin.jvm.Throws

/**
 * 通过此接口获取输入流，以兼容文件、FileProvider方式获取到的图片
 * Get the input stream through this interface, and obtain the picture using compatible files and FileProvider
 */
/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: tt
 * Author: Victor
 * Date: 2023/11/15 15:39
 * Description:
 * 通过此接口获取输入流，以兼容文件、FileProvider方式获取到的图片
 * Get the input stream through this interface, and obtain the picture using compatible files and FileProvider
 * -----------------------------------------------------------------
 */
interface InputStreamProvider<T> {
    @Throws(IOException::class)
    fun rewindAndGet(): InputStream
    fun close()
    val src: T //源数据
}