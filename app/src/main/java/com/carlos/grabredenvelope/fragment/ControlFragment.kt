package com.carlos.grabredenvelope.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.carlos.cutils.util.LogUtils
import com.carlos.grabredenvelope.R
import com.carlos.grabredenvelope.activity.MainActivity
import com.carlos.grabredenvelope.dao.WechatControlVO
import com.carlos.grabredenvelope.data.RedEnvelopePreferences
import com.carlos.grabredenvelope.databinding.FragmentControlBinding

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
 * Created by 小不点 on 2016/5/27.
 */
class ControlFragment : BaseFragment(R.layout.fragment_control), SeekBar.OnSeekBarChangeListener {
    private var _binding: FragmentControlBinding? = null
    private val binding get() = _binding!!
    private var wechatControlVO = WechatControlVO()
    private var t_putong: Int = 0
    private var t_lingqu: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        val view = binding.root

        init(view)

        loadSaveData()
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun init(view: View) {
        binding.cbQqControl.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.cbQqControl.isChecked = !isChecked
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            Toast.makeText(view.context, "辅助功能找到（抢微信红包）开启或关闭。", Toast.LENGTH_SHORT)
                .show()
        }

        binding.cbWechatNotificationControl.setOnCheckedChangeListener { buttonView, isChecked ->
            wechatControlVO.isMonitorNotification = isChecked
            RedEnvelopePreferences.wechatControl = wechatControlVO
        }
        binding.cbWechatChatControl.setOnCheckedChangeListener { buttonView, isChecked ->
            wechatControlVO.isMonitorChat = isChecked
            RedEnvelopePreferences.wechatControl = wechatControlVO
        }
        binding.cbIfGrabSelf.setOnCheckedChangeListener { buttonView, isChecked ->
            wechatControlVO.ifGrabSelf = isChecked
            RedEnvelopePreferences.wechatControl = wechatControlVO
        }

        binding.sbQqPutong.setOnSeekBarChangeListener(this)
        binding.sbQqLingqu.setOnSeekBarChangeListener(this)

        binding.cbCustomClick.setOnCheckedChangeListener { buttonView, isChecked ->
            wechatControlVO.isCustomClick = isChecked
            RedEnvelopePreferences.wechatControl = wechatControlVO
        }

        binding.etPointX.addTextChangedListener {
            if (binding.etPointX.text.isNullOrEmpty()) {
                wechatControlVO.pointX = 0
            }else {
                wechatControlVO.pointX = binding.etPointX.text.toString().toLong()
            }
            RedEnvelopePreferences.wechatControl = wechatControlVO
        }

        binding.etPointY.addTextChangedListener {
            if (binding.etPointY.text.isNullOrEmpty()) {
                wechatControlVO.pointY = 0
            }else {
                wechatControlVO.pointY = binding.etPointY.text.toString().toLong()
            }
            RedEnvelopePreferences.wechatControl = wechatControlVO
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.llCustomClick.visibility = View.VISIBLE
        }else {
            binding.llCustomClick.visibility = View.GONE
        }

        binding.etTextFilters.setText(RedEnvelopePreferences.grabFilter)
        binding.etTextFilters.addTextChangedListener {
            RedEnvelopePreferences.grabFilter = binding.etTextFilters.text.toString()
        }
    }


    private fun loadSaveData() {
        binding.cbWechatNotificationControl.isChecked =
            RedEnvelopePreferences.wechatControl.isMonitorNotification
        binding.cbWechatChatControl.isChecked = RedEnvelopePreferences.wechatControl.isMonitorChat
        binding.cbIfGrabSelf.isChecked = RedEnvelopePreferences.wechatControl.ifGrabSelf
        LogUtils.d("wechatControl:" + RedEnvelopePreferences.wechatControl.toString())

        wechatControlVO = RedEnvelopePreferences.wechatControl
        t_putong = wechatControlVO.delayOpenTime
        binding.tvQqPutong.text = "领取红包延迟时间：" + t_putong + "s"
        binding.sbQqPutong.progress = t_putong

        t_lingqu = wechatControlVO.delayCloseTime
        binding.sbQqLingqu.progress = t_lingqu - 1
        if (t_lingqu == 11) {
            binding.tvQqLingqu.text = "红包领取页关闭时间：" + "不关闭"
        } else {
            binding.tvQqLingqu.text = "红包领取页关闭时间：" + t_lingqu + "s"
        }
        binding.cbCustomClick.isChecked = RedEnvelopePreferences.wechatControl.isCustomClick
        binding.etPointX.setText(RedEnvelopePreferences.wechatControl.pointX.toString())
        binding.etPointY.setText(RedEnvelopePreferences.wechatControl.pointY.toString())

        val mainActivity = activity as MainActivity
        updateControlView(mainActivity.checkStatus())
    }

    fun updateControlView(boolean: Boolean) {
        if (boolean) binding.cbQqControl?.setButtonDrawable(R.mipmap.switch_on)
        else binding.cbQqControl?.setButtonDrawable(R.mipmap.switch_off)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.sb_qq_putong -> {
                LogUtils.d("sb_qq_putong:$progress")
                t_putong = progress
                binding.tvQqPutong.text = "领取红包延迟时间：" + t_putong + "s"
                wechatControlVO.delayOpenTime = t_putong
                RedEnvelopePreferences.wechatControl = wechatControlVO
            }

            R.id.sb_qq_lingqu -> {
                LogUtils.d("sb_qq_lingqu:$progress")
                t_lingqu = progress + 1
                binding.tvQqLingqu.text = "红包领取页关闭延迟时间：" + t_lingqu + "s"
                if (t_lingqu == 11) {
                    binding.tvQqLingqu.text = "红包领取页关闭时间：" + "不关闭"
                }
                wechatControlVO.delayCloseTime = t_lingqu
                RedEnvelopePreferences.wechatControl = wechatControlVO
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }
}
