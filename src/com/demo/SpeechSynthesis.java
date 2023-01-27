package com.demo;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class SpeechSynthesis {
    // This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    private static String speechKey = "e96bb1550cc44c96a9983242dccf7ea3";
    private static String speechRegion = "eastasia";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //
        SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
        //日语 ja-JP
        // 中午 zh-CN
        speechConfig.setSpeechSynthesisVoiceName("zh-CN-XiaoyanNeural");
        //SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

        // 保存至文件
        //AudioConfig audioConfig = AudioConfig.fromWavFileOutput("resource.wav");

        // Get text from the console and synthesize to the default speaker.
        System.out.println("语音测试文本");
        String text = new Scanner(System.in).nextLine();
        if (text.isEmpty())
        {
            return;
        }
        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);
        //synthesizer.SpeakText(text);
        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(text).get();

        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + text + "]");
        }
        else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
            }
        }

        System.exit(0);
    }
}