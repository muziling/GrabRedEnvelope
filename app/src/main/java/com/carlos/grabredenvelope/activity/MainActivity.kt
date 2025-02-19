package com.carlos.grabredenvelope.activity

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.carlos.cutils.base.activity.CBaseAccessibilityActivity
import com.carlos.cutils.base.adapter.CBaseMyPagerAdapter
import com.carlos.cutils.listener.PermissionListener
import com.carlos.grabredenvelope.databinding.ActivityMainBinding
import com.carlos.grabredenvelope.fragment.*

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
 */

/**
 * Github: https://github.com/xbdcc/.
 * Created by 小不点 on 2016/2/14.
 */
open class MainActivity : CBaseAccessibilityActivity() {

    private lateinit var binding: ActivityMainBinding

    private val WECHAT_SERVICE_NAME = "com.carlos.grabredenvelope/.services.WechatService"

    var fragments = mutableListOf<Fragment>(ControlFragment(), GuideFragment(), AboutFragment(),
        CodeFragment(), RewardFragment(), RecordFragment(), EmojiFragment()
    )
    private var titles = mutableListOf("控制", "教程", "说明", "源码", "打赏", "微信", "表情")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()

        getPermissions()
        addListener()
    }

    private fun initView() {
        val adapter = CBaseMyPagerAdapter(supportFragmentManager, fragments, titles)
        binding.viewPager.adapter = adapter
        binding.slidingTabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.offscreenPageLimit = 2
    }

    private fun addListener() {
        addAccessibilityServiceListener(object :
            AccessibilityServiceListeners {
            override fun updateStatus(boolean: Boolean) {
                val controlFragment = fragments[0] as ControlFragment
                controlFragment.updateControlView(boolean)
            }
        }, WECHAT_SERVICE_NAME)
    }

    private fun getPermissions() {
        requestPermission(100, object : PermissionListener {
            override fun permissionSuccess() {}
            override fun permissionFail() {}
        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    fun checkItem(item: Int) {
        binding.viewPager.currentItem = item
    }

}