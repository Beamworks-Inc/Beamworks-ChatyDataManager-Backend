package com.example.springkotlintemplate.Sample.CoverageTestCode

import com.example.springkotlintemplate.Sample.CoverageTestCode.ArithmeticOperator

class TestArithmeticOperator : ArithmeticOperator {
    override fun plus(a:Int, b:Int): Int{
        return a+b;
    }
    override fun minus(a:Int, b:Int): Int {
        return a-b
    }
    override fun multiply(a:Int, b:Int): Int {
        return a*b
    }
    override fun divide(a:Int, b:Int): Int {
        return a/b
    }
}