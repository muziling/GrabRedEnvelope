package com.carlos.grabredenvelope.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.carlos.cutils.thirdparty.AlipayReward
import com.carlos.cutils.thirdparty.WechatReward
import com.carlos.cutils.util.ToastUtil
import com.carlos.grabredenvelope.R
import com.carlos.grabredenvelope.databinding.FragmentRewardBinding
import com.carlos.grabredenvelope.util.BitmapUtils
import java.io.File

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
 * Created by Carlos on 2019/2/23.
 */
class RewardFragment : BaseFragment(R.layout.fragment_reward) {
    private var _binding: FragmentRewardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardBinding.inflate(inflater, container, false)
        val view = binding.root
        init(view)
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun init(view: View) {
        binding.bAlipayReward.setOnClickListener { AlipayReward(view.context) }
        binding.bAlipayReward.setOnClickListener { AlipayReward(view.context) }
        binding.bWechatReward.setOnClickListener {
            WechatReward(view.context)
        }
        binding.ivAlipay.setOnLongClickListener {
            val filedir = filedir
            val output = File(filedir, "xbd_alipay.jpg")
            if (!output.exists()) {
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.alipay)
                BitmapUtils.saveBitmap(view.context, output, bitmap)
            }
            ToastUtil.Builder(view.context).setLongToast().setText("已保存到:" + output.absolutePath)
                .build()
            true
        }
        binding.ivWechat.setOnLongClickListener {
            val filedir = filedir
            val output = File(filedir, "xbd_wechat.jpg")
            if (!output.exists()) {
                val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.wechat_reward)
                BitmapUtils.saveBitmap(view.context, output, bitmap)
            }
            ToastUtil.Builder(view.context).setLongToast().setText("已保存到:" + output.absolutePath)
                .build()
            true
        }
    }
    /**
     * 得到安装路径
     * @return
     */
    internal val filedir: File
        get() {
            val sd = Environment.getExternalStorageDirectory()
            val path = sd.path + "/GrabRedEnvelope"
            val filedir = File(path)
            if (!filedir.exists()) {
                filedir.mkdir()
                Log.e("GrabRedEnvelope", "新建一个文件夹")
            }
            return filedir
        }

}