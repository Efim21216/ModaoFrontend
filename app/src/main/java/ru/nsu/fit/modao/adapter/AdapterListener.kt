package ru.nsu.fit.modao.adapter

interface AdapterListener<T> {
    fun onClickItem(item: T)
}