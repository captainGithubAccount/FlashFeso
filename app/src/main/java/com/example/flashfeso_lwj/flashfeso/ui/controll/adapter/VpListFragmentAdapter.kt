package com.example.flashfeso_lwj.flashfeso.ui.controll.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class VpListFragmentAdapter(
    private val fm: FragmentManager,
    private val list: List<ListFragmentEntity>
): FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = list.count()

    override fun getItem(position: Int): Fragment = list[position].mFragment

    //使用getPageTitle()来设置TabLayout中选项卡上显示的文本
    override fun getPageTitle(position: Int): CharSequence? = list[position].mTitle

     class ListFragmentEntity( val mFragment: Fragment,  val mTitle: String)

}