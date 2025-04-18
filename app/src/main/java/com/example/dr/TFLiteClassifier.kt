package com.example.dr

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TFLiteClassifier(context: Context) {
    private var interpreter: Interpreter
    private val inputSize = 258
    private val outputSize = 50 // number of classes

    // Paste from Python
    private val featureMeans = floatArrayOf(202.012771f,
        196.423127f,308.393195f,        330.267599f,        141.188069f,        297.448758f,        140.645381f,        118.801734f,        273.536452f,        129.140436f,        28.538433f,        19.694047f,
        52.697909f,        19.069612f,        177.248833f,        125.319669f,        103.701573f,        106.877957f,        119.344381f,        121.500374f,        120.971885f,        117.953703f,
        113.731112f,        109.053316f,        104.363901f,        99.920074f,        96.155902f,        93.250553f,        91.377562f,        90.672355f,        91.131986f,        92.572344f,
        94.848977f,
        97.781617f,
        101.169695f,
        104.986191f,
        109.051666f,
        113.280210f,
        117.563088f,
        121.823309f,
        126.118396f,
        130.175543f,
        134.081610f,
        137.802778f,
        141.629792f,
        145.219110f,
        148.618282f,
        151.917416f,
        155.103734f,
        158.011707f,
        160.856745f,
        163.378339f,
        165.835785f,
        168.133961f,
        170.617659f,
        172.780118f,
        174.817800f,
        176.945912f,
        178.886582f,
        180.873183f,
        182.910845f,
        185.014836f,
        187.138690f,
        189.503152f,
        192.064277f,
        194.519559f,
        197.196522f,
        199.886359f,
        202.868537f,
        205.804976f,
        209.250671f,
        212.590592f,
        216.134261f,
        219.896420f,
        223.707717f,
        227.560387f,
        231.932782f,
        236.162654f,
        240.485227f,
        244.233533f,
        248.133938f,
        251.831971f,
        109.641812f,
        109.393920f,
        109.179877f,
        108.974097f,
        108.665008f,
        108.430537f,
        108.126624f,
        107.824083f,
        107.472043f,
        107.138226f,
        106.834235f,
        106.513385f,
        106.220588f,
        105.880694f,
        105.676600f,
        105.484545f,
        105.300395f,
        105.243031f,
        105.273867f,
        105.334193f,
        105.285882f,
        105.484563f,
        105.492016f,
        105.511375f,
        105.497477f,
        105.505431f,
        105.519580f,
        105.460971f,
        105.348197f,
        105.235770f,
        105.071680f,
        104.862753f,
        104.566499f,
        104.249025f,
        103.937939f,
        103.580629f,
        103.255729f,
        102.996014f,
        102.781648f,
        102.646019f,
        102.527256f,
        102.406429f,
        102.400959f,
        102.354525f,
        102.484502f,
        102.572537f,
        102.773296f,
        103.036945f,
        103.237460f,
        103.575924f,
        103.969603f,
        104.427858f,
        104.914765f,
        105.413767f,
        105.943566f,
        106.475690f,
        0.000000f,
        14.438075f,
        25.375658f,
        35.094957f,
        44.946080f,
        55.137249f,
        65.658290f,
        76.351450f,
        87.137135f,
        97.829045f,
        108.418355f,
        118.832723f,
        129.094897f,
        139.103264f,
        148.779711f,
        158.086954f,
        166.946435f,
        175.356680f,
        183.355690f,
        190.932871f,
        198.116602f,
        204.853302f,
        211.334498f,
        217.223234f,
        222.688997f,
        227.747682f,
        232.674976f,
        237.023905f,
        240.995882f,
        244.632631f,
        248.025989f,
        251.100733f,
        253.999495f,
        256.437756f,
        258.856509f,
        261.065608f,
        263.425937f,
        265.604395f,
        267.628768f,
        269.671116f,
        271.655652f,
        273.627344f,
        275.228817f,
        276.866932f,
        278.497389f,
        279.973920f,
        281.219458f,
        282.514809f,
        284.558693f,
        286.498181f,
        288.996990f,
        291.614943f,
        294.881245f,
        298.057295f,
        301.535897f,
        305.115963f,
        308.639743f,
        312.126552f,
        315.949053f,
        319.654754f,
        323.310403f,
        326.442405f,
        329.538505f,
        332.562688f,
        68.651055f,
        69.487813f,
        70.318301f,
        71.210681f,
        71.969486f,
        72.725244f,
        73.505562f,
        74.199115f,
        74.816679f,
        75.444376f,
        76.082072f,
        76.666031f,
        77.261940f,
        77.814200f,
        78.355609f,
        78.960851f,
        79.509472f,
        80.164598f,
        80.701036f,
        81.290223f,
        81.980054f,
        82.687683f,
        83.348608f,
        83.996157f,
        84.607226f,
        85.142768f,
        85.548175f,
        85.841248f,
        85.981787f,
        85.876769f,
        85.822047f,
        85.808460f,
        85.841210f,
        86.159132f,
        86.348593f,
        86.473187f,
        86.860592f,
        87.307595f,
        87.782578f,
        88.330837f,
        88.900450f,
        89.536557f,
        90.201430f,
        90.870863f,
        91.629399f,
        92.425055f,
        93.300840f,
        94.183822f,
        95.112027f,
        96.110661f,
        97.140431f,
        98.225449f,
        99.319773f,
        100.422832f,
        101.561417f,
        102.712109f/* paste here */)
    private val featureStds = floatArrayOf(
        89.069575f, 87.521141f, 96.655026f, 97.501247f, 90.664219f, 113.877231f, 95.077505f, 86.608779f, 102.238788f, 91.436998f, 10.793937f, 16.244883f, 22.603088f, 17.364617f, 52.447436f, 67.458037f, 42.499839f, 60.244903f, 67.721034f, 66.121901f, 62.675605f, 58.597182f, 55.070777f, 52.526773f, 51.067534f, 50.604848f, 50.692793f, 50.993326f, 51.330009f, 51.599368f, 51.868300f,
        52.132162f, 52.288121f, 52.423139f, 52.423482f, 52.356841f, 52.198803f, 52.133349f, 52.257493f, 52.532493f, 53.208804f, 54.034075f, 54.990105f, 55.968697f, 57.183581f, 58.039542f, 58.669572f, 59.105085f, 59.292494f, 59.199323f, 58.870556f, 58.427812f, 58.107614f, 57.880800f, 58.173613f, 58.581130f, 59.095553f, 59.903108f, 60.759701f, 61.722053f, 62.640856f, 63.662429f, 64.565082f, 65.434520f, 66.278046f, 67.091700f,
        67.875130f, 68.761382f, 69.864611f, 71.039544f, 72.078361f, 73.386162f, 74.688160f, 75.999768f, 77.355528f, 78.650210f, 79.703798f, 80.474848f, 81.196461f, 81.819638f, 82.318927f, 82.713850f, 47.018339f, 47.036437f, 47.112251f, 47.227094f, 47.115124f, 47.182425f, 47.141702f, 47.109106f, 46.992876f, 46.886692f, 46.747665f, 46.544952f, 46.244313f, 45.862851f, 45.609517f, 45.374932f, 45.013859f, 44.930730f, 45.101060f, 45.274774f, 45.107259f, 45.406191f,
        45.390374f, 45.466956f, 45.590669f, 45.825655f, 46.230511f, 46.495413f, 46.806021f, 47.100120f, 47.291269f, 47.328884f, 47.230038f, 46.929082f, 46.716196f, 46.486481f, 46.259689f, 46.218883f, 46.346232f, 46.766128f, 47.269455f, 47.803158f,
        48.618669f, 49.317695f, 50.406263f, 51.466304f, 52.752208f, 54.258024f, 55.666102f, 57.394569f, 59.273816f, 61.329288f, 63.528004f, 65.755693f, 68.076157f, 70.411327f, 1.000000f, 5.460820f, 9.692750f, 14.146354f, 18.114017f, 21.711258f, 24.952448f, 27.764202f, 30.374807f, 32.815634f, 35.256729f, 37.790167f, 40.383948f, 43.073098f, 45.852424f, 48.718705f, 51.623276f, 54.562699f, 57.562371f, 60.641026f, 63.847183f, 66.888414f, 70.072424f, 72.914710f, 75.762069f, 78.350047f, 81.022019f, 83.167437f, 84.994056f, 86.789567f, 88.283817f, 89.371552f, 90.405285f, 91.175122f, 92.092372f, 93.144352f, 94.713138f, 96.083323f, 97.578131f, 99.310070f, 100.931571f, 102.681754f, 104.566725f, 106.435453f,
        108.372967f, 110.456054f, 112.669501f, 114.698887f, 116.369229f, 117.981941f, 119.447316f, 120.913559f, 121.850284f, 122.921980f, 123.689304f, 124.316752f, 124.956777f, 125.451377f, 125.690501f, 125.593839f, 125.509141f, 125.237298f, 124.964365f, 124.302091f, 96.438977f, 97.063235f, 97.699419f, 98.418595f, 98.941519f, 99.434318f, 99.980923f, 100.371103f, 100.633811f, 100.893421f, 101.135024f, 101.240677f, 101.298779f, 101.310716f, 101.246485f, 101.223775f, 101.144085f,
        101.215668f, 101.102156f, 101.042531f, 101.167903f, 101.419282f, 101.588054f, 101.600061f, 101.608670f, 101.503747f, 101.171682f, 100.639096f, 99.708076f, 98.270494f, 96.992323f, 95.853494f, 94.641163f, 94.084076f, 93.133144f, 92.050382f, 91.517645f, 91.128577f, 90.835444f, 90.740250f, 90.676073f, 90.740959f, 90.950797f, 91.076831f, 91.440417f, 91.919889f, 92.580029f, 93.257749f, 94.018847f, 94.975000f, 96.017663f, 97.208157f, 98.506832f, 99.876835f, 101.383918f, 102.935130f
    )

    init {
        val assetFileDescriptor = context.assets.openFd("model.tflite")
        val fileInputStream = assetFileDescriptor.createInputStream()
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val modelBuffer = fileChannel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

        interpreter = Interpreter(modelBuffer)

    }

    private fun standardize(input: FloatArray): FloatArray {
        return FloatArray(input.size) { i ->
            (input[i] - featureMeans[i]) / featureStds[i]
        }
    }

    fun predict(input: List<Double>): Int {
        require(input.size == inputSize)

        val floatInput = input.map { it.toFloat() }.toFloatArray()
        val standardizedInput = standardize(floatInput)

        val inputBuffer = ByteBuffer.allocateDirect(4 * inputSize).order(ByteOrder.nativeOrder())
        standardizedInput.forEach { inputBuffer.putFloat(it) }

        val output = Array(1) { FloatArray(outputSize) }
        interpreter.run(inputBuffer, output)

        return output[0].indices.maxByOrNull { output[0][it] } ?: -1
    }
}
