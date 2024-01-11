package com.example.my_prac

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.my_prac.ui.theme.My_pracTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            My_pracTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch(Dispatchers.IO) {
                    flowOfStrings.collect { data ->
                        Log.d("jun", "flowOfStrings : $data")
                    }
                }
                //플로우오브스트링은 디스패쳐를 사용하여 별도의 코루틴에서 실행이 되고
                //프린트넘버는 리핏온라이플사이클코루틴내에서 실행됨 만약 두 플로우가 순차적으로 진행되길 원하면 launch를 없애 그러면 딜레이 되면서 순차적으로 진행댐 ㄷ독립적으로 실행원하면
                printNumbers().collect { data ->
                    Log.d("jun", ">> printNumbers() : $data")
                }
            }
        }
    }

        private val flowOfStrings = flow {
            for (number in 0..10) {
                emit("Emitting: $number")
                delay(1000L)
            }
        }

        private fun printNumbers() = flow {
            for (i in 1..10) {
                emit(i) // emit(value: Int)
                Log.d("jun", "$i emit됨")
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "NFT $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    My_pracTheme {
        Greeting("Market!")
    }
}