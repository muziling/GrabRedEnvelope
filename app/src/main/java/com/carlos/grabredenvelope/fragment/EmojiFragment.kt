package com.carlos.grabredenvelope.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.carlos.grabredenvelope.R
import com.carlos.grabredenvelope.data.RedEnvelopePreferences
import com.carlos.grabredenvelope.databinding.FragmentEmojiBinding

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
 * Created by Carlos on 2021/2/8.
 */
class EmojiFragment : BaseFragment(R.layout.fragment_emoji) {
    private var _binding: FragmentEmojiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmojiBinding.inflate(inflater, container, false)
        val view = binding.root
        init()
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        init()
//    }

    private fun init() {
        binding.tvControl.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            Toast.makeText(view?.context, "辅助功能找到（自动发送表情）开启或关闭。", Toast.LENGTH_SHORT)
                .show()
        }

        binding.etEmoji.setText(RedEnvelopePreferences.autoText)
        binding.etEmoji.doAfterTextChanged {
            RedEnvelopePreferences.autoText = binding.etEmoji.text.toString()
        }

        binding.npTimes.minValue = 0
        binding.npTimes.maxValue = 100
        binding.npTimes.value = RedEnvelopePreferences.emojiTimes
        binding.npTimes.setOnValueChangedListener { picker, oldVal, newVal ->
            RedEnvelopePreferences.emojiTimes = newVal
        }

        binding.npInterval.minValue = 0
        binding.npInterval.maxValue = 3000
        binding.npInterval.value = RedEnvelopePreferences.emojiInterval
        binding.npInterval.setOnValueChangedListener { picker, oldVal, newVal ->
            RedEnvelopePreferences.emojiInterval = newVal
        }
    }

}