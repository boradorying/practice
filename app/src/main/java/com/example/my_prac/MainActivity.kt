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
                printNumbers().collect { data ->
                    Log.d("jun", ">> printNumbers() : $data")
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch(Dispatchers.IO) {
                    simpleStringFlow().collect { data ->
                        Log.d("jun", "Received from flow: $data")
                    }
                }
            }
        }
    }
    private fun simpleStringFlow(): Flow<String> = flow {
        val strings = listOf("Hello", "World", "From", "Flow")
        for (string in strings) {
            emit(string)
            delay(1000L) // 1초 대기
        }
    }//예시문추가
    //flow
    //livedata



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