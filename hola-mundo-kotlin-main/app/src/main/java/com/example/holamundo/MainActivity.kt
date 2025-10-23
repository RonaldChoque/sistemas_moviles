package com.example.holamundo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.holamundo.ui.theme.HolaMundoTheme
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.PI
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HolaMundoTheme {
                HolaMundoScreen()
            }
        }
    }
}

@Composable
fun HolaMundoScreen() {
    var isClicked by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(false) }
    var clickCount by remember { mutableIntStateOf(0) }
    var showConfetti by remember { mutableStateOf(false) }
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked) {
            Color(0xFF6A4C93)
        } else {
            Color(0xFF4ECDC4)
        },
        animationSpec = tween(1000),
        label = "backgroundColor"
    )
    
    val textScale by animateFloatAsState(
        targetValue = if (isClicked) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "textScale"
    )
    
    val cardScale by animateFloatAsState(
        targetValue = if (isClicked) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "cardScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        backgroundColor,
                        backgroundColor.copy(alpha = 0.7f),
                        Color(0xFF2A2D3A)
                    ),
                    radius = 1200f
                )
            )
    ) {
        // Animated background particles
        AnimatedParticles()
        
        // Confetti effect
        if (showConfetti) {
            ConfettiEffect {
                showConfetti = false
            }
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .scale(cardScale)
                    .clickable {
                        isClicked = !isClicked
                        clickCount++
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.95f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Hola Mundo! 🌍",
                        fontSize = (32 * textScale).sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2A2D3A),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.scale(textScale)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = if (clickCount == 0) {
                            "¡Toca el botón para saber que chucha hace!"
                        } else {
                            "¡Increíble! Has tocado ${clickCount} ${if (clickCount == 1) "vez" else "veces"} 🎉"
                        },
                        fontSize = 16.sp,
                        color = Color(0xFF666666),
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = "Mi primera app Android",
                        fontSize = 14.sp,
                        color = Color(0xFF999999),
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Interactive heart button
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = if (isFavorite) {
                                listOf(Color(0xFFFF6B6B), Color(0xFFFF8E53))
                            } else {
                                listOf(Color(0xFFE0E0E0), Color(0xFFF5F5F5))
                            }
                        )
                    )
                    .clickable {
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            showConfetti = true
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = if (isFavorite) Color.White else Color(0xFF666666),
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = if (isFavorite) {
                    "No sé que poner aqui 🧑🏽‍💻"
                } else {
                    "¿Te gusta la app? Dale al botón"
                },
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AnimatedParticles() {
    var time by remember { mutableFloatStateOf(0f) }
    
    LaunchedEffect(Unit) {
        while (true) {
            time += 0.016f // ~60 FPS
            kotlinx.coroutines.delay(16)
        }
    }
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawParticles(this, time)
    }
}

fun drawParticles(drawScope: DrawScope, time: Float) {
    val width = drawScope.size.width
    val height = drawScope.size.height
    
    // Draw floating particles
    repeat(20) { i ->
        val x = (width * 0.1f + (i * 50f) % width + sin(time + i) * 30f) % width
        val y = (height * 0.1f + (i * 80f) % height + sin(time * 0.7f + i * 0.5f) * 40f) % height
        val alpha = (sin(time + i * 0.3f) * 0.3f + 0.7f).coerceIn(0.1f, 0.4f)
        
        drawScope.drawCircle(
            color = Color.White.copy(alpha = alpha),
            radius = (3f + sin(time + i) * 2f).coerceAtLeast(1f),
            center = Offset(x, y)
        )
    }
}

@Composable
fun ConfettiEffect(onAnimationEnd: () -> Unit = {}) {
    var confettiPieces by remember { mutableStateOf(generateConfettiPieces()) }
    var animationTime by remember { mutableFloatStateOf(0f) }
    
    LaunchedEffect(Unit) {
        val duration = 3000L // 3 seconds
        val startTime = System.currentTimeMillis()
        
        while (System.currentTimeMillis() - startTime < duration) {
            animationTime = (System.currentTimeMillis() - startTime) / duration.toFloat()
            kotlinx.coroutines.delay(16) // ~60 FPS
        }
        onAnimationEnd()
    }
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        confettiPieces.forEach { piece ->
            drawConfettiPiece(this, piece, animationTime)
        }
    }
}

data class ConfettiPiece(
    val startX: Float,
    val startY: Float,
    val velocityX: Float,
    val velocityY: Float,
    val color: Color,
    val size: Float,
    val rotation: Float,
    val rotationSpeed: Float
)

fun generateConfettiPieces(): List<ConfettiPiece> {
    val colors = listOf(
        Color(0xFFFF6B6B), // Rosa
        Color(0xFFFF8E53), // Naranja
        Color(0xFF4ECDC4), // Teal
        Color(0xFF6A4C93), // Púrpura
        Color(0xFFFFD93D), // Amarillo
        Color(0xFF6BCF7F), // Verde
        Color(0xFF4D96FF), // Azul
        Color(0xFFFF9FF3)  // Rosa claro
    )
    
    return (0..60).map {
        val angle = kotlin.random.Random.nextFloat() * 2 * PI
        val speed = kotlin.random.Random.nextFloat() * 300f + 100f
        
        ConfettiPiece(
            startX = 500f, // Centro horizontal aproximado
            startY = 600f, // Posición aproximada del corazón
            velocityX = cos(angle).toFloat() * speed,
            velocityY = sin(angle).toFloat() * speed - 200f, // Impulso hacia arriba
            color = colors.random(),
            size = kotlin.random.Random.nextFloat() * 6f + 3f,
            rotation = kotlin.random.Random.nextFloat() * 360f,
            rotationSpeed = (kotlin.random.Random.nextFloat() - 0.5f) * 720f
        )
    }
}

fun drawConfettiPiece(drawScope: DrawScope, piece: ConfettiPiece, animationTime: Float) {
    val gravity = 980f // Gravedad
    val currentTime = animationTime * 3f // 3 segundos de animación
    
    val x = piece.startX + piece.velocityX * currentTime
    val y = piece.startY + piece.velocityY * currentTime + 0.5f * gravity * currentTime * currentTime
    val rotation = piece.rotation + piece.rotationSpeed * currentTime
    val alpha = (1f - animationTime).coerceAtLeast(0f)
    
    if (x >= 0 && x <= drawScope.size.width && y >= 0 && y <= drawScope.size.height && alpha > 0) {
        drawScope.drawCircle(
            color = piece.color.copy(alpha = alpha),
            radius = piece.size,
            center = Offset(x, y)
        )
    }
}
