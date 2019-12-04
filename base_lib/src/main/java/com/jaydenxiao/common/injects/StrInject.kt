package com.jaydenxiao.common.injects

/**
 * Created by 13198 on 2018/9/27.
 * @desc 对字符串进行处理
 */

/**
 * @desc: 在文本头部插入"#"
 */
fun String.insertOctAtHead(): String {
    return insertStrAtHead("#")
}

/**
 * @desc: 在文本头部插入美元符号
 */
fun String.insertDollarAtHead(): String {
    return insertStrAtHead("$")
}

/**
 * @desc: 在文本头部插入人名币符号
 */
fun String.insertRMBAtHead(): String {
    return insertStrAtHead("￥")
}

/**
 * @desc: 在文本头部插入指定字符串
 */
fun String.insertStrAtHead(str: String): String {
    return if (!this?.contains(str)) {
        "str$this"
    } else {
        this
    }
}

/**
 *  @desc: 使用StringBuffer将多个字符串拼接
 */
fun getArgsStr(vararg strs: Any?): String {
    val sb = StringBuffer()
    for (it in strs) {
        sb.append(it.toString())
        sb.append("\r\n")
    }
    return sb.toString()
}