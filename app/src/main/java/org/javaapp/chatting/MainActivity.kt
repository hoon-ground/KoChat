package org.javaapp.chatting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.javaapp.chatting.chat.ChatFragment
import org.javaapp.chatting.databinding.ActivityMainBinding
import org.javaapp.chatting.mypage.MyPageFragment
import org.javaapp.chatting.user.UserFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding // view binding
    private lateinit var auth : FirebaseAuth // Firebase auth
    private var currentUser : FirebaseUser? = null // Firebase currentUser

    private val userFragment = UserFragment() // 사용자 리스트 프래그먼트
    private val chatFragment = ChatFragment() // 단체 채팅 프래그먼트
    private val myPageFragment = MyPageFragment() // 마이페이지 프래그먼트

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth // Firebase auth
        currentUser = auth.currentUser // Firebase currentUser

        // 현재 사용자 정보 확인 (현재 로그인이 되어있는지 확인)
        if (currentUser == null) { // 로그인이 되어있지 않으면
            // 로그인 액티비티로 이동
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)

            // 메인 액티비티 종료
            finish()
            return
        }


        // 앱 실행 시 기본으로 보여줄 프래그먼트로 UserListFragment 설정
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, userFragment).commit()

        // 바텀 네비게이션뷰 리스너 설정
        binding.bottomNavigation.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId) {
                R.id.user_list -> {
                    replaceFragment(userFragment) // 프래그먼트 교체
                    return@setOnItemSelectedListener true
                }
                R.id.chat_msg -> {
                    replaceFragment(chatFragment) // 프래그먼트 교체
                    return@setOnItemSelectedListener true
                }
                R.id.mypage -> {
                    replaceFragment(myPageFragment) // 프래그먼트 교체
                    return@setOnItemSelectedListener true
                }
                else ->  {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    // 프래그먼트 교체
    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }


    }

}