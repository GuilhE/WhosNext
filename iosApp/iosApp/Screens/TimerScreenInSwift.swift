import SwiftUI
import KMPObservableViewModelSwiftUI
import WhosNextShared
import WhosNextComposables

private let COUNTDOWN_STEP_ANIMATION_DURATION: Double = Double(UtilsKt.COUNTDOWN_STEP_ANIMATION_DURATION) / 1000
private let STOP_ANIMATION_DURATION: Double = Double(UtilsKt.STOP_ANIMATION_DURATION) / 1000
private let RESTART_ANIMATION_DURATION: Double = Double(UtilsKt.RESTART_ANIMATION_DURATION) / 1000

struct TimerScreenInSwift: View {
    let onClose: () -> Void
    private let soundPlayer = TimesUpSoundPlayer()
    private let countDownAnimation = Animation.linear(duration: COUNTDOWN_STEP_ANIMATION_DURATION)
    private let stopAnimation =  EasingFunctions.linearOutSlowInEasing(duration: STOP_ANIMATION_DURATION)
    private let restartAnimation = EasingFunctions.linearOutSlowInEasing(duration: RESTART_ANIMATION_DURATION)
    
    @StateViewModel private var viewModel = ViewModels().timerViewModel()
    
    var body: some View {
        if(viewModel.uiState.isRestarting) {
            soundPlayer.play()
        }
        return Timer(
            uiState: viewModel.uiState,
            countDownAnimation: countDownAnimation,
            restartAnimation: restartAnimation,
            stopAnimation: stopAnimation,
            backgroundColor: ColorsPallete.toColor(index: viewModel.uiState.backgroundIndex),
            onClose: { onClose() },
            onStart: { viewModel.start() },
            onPause: { viewModel.pause() },
            onReset: { viewModel.reset() },
            onStop: { viewModel.stop() },
            onSettingTime: { viewModel.settingTime() },
            onSetTime: { time in viewModel.setTime(seconds: time) }
        ).onAppear() {
            //print("Swift\tobject \(Unmanaged.passUnretained(viewModel).toOpaque())")
        }
    }
}

#Preview {
    TimerScreenInSwift(onClose: {})
        .ignoresSafeArea()
}
