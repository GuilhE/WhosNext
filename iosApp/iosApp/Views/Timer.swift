import SwiftUI
import WhosNextShared
import WhosNextComposables

struct Timer: View {
    let uiState: TimerUiState
    let countDownAnimation: Animation
    let restartAnimation: Animation
    let stopAnimation: Animation
    let backgroundColor: Color
    let onClose: () -> Void
    let onStart: () -> Void
    let onPause: () -> Void
    let onReset: () -> Void
    let onStop: () -> Void
    let onSettingTime: () -> Void
    let onSetTime: (Int32) -> Void
    
    private let DRAG_STEP_LAG: Float = 400
    
    @State private var isDraggingLeft = false
    @State private var lastDrag: Float = 0.0
    @State private var lastDragY: CGFloat = 0.0
    @State private var minutes: Int32 = 0
    @State private var seconds: Int32 = 0
    @State private var progress : Float = 0.0
    
    private var timeDrag: some Gesture {
        DragGesture()
            .onChanged { value in
                if (uiState.isSettingTimer) {
                    let left = value.location.x < UIScreen.main.bounds.size.width / 2
                    if (lastDrag > DRAG_STEP_LAG) {
                        lastDrag = 0.0
                        let res: Int32 = lastDragY - value.location.y > 0 ? 1 : -1
                        if (left) {
                            minutes += (0...59).contains(minutes + res) ? res : 0
                        } else {
                            seconds += (0...59).contains(seconds + res) ? res : 0
                        }
                    } else {
                        //to "reduce" drag velocity and reset when changing sides
                        let verticalDrag = isDraggingLeft == left ? value.translation.height : 0
                        lastDrag += abs(Float(verticalDrag))
                    }
                    isDraggingLeft = left
                    lastDragY = value.location.y
                } else {
                    if (uiState.isStopped()) {
                        lastDrag = 0
                        lastDragY = 0
                        onSettingTime()
                    }
                }
            }
            .onEnded { value in
                if (uiState.isSettingTimer) {
                    onSetTime(UtilsKt.minutesToSeconds(time: minutes) + seconds)
                }
            }
    }
    
    var body: some View {
        ZStack {
            backgroundColor
                .animation(uiState.isRestarting ? restartAnimation : .default, value: uiState.isRestarting)
            Image(ImageRecources.bgTexture)
                .resizable()
            VStack(alignment: .center) {
                HeaderLogo(
                    isRestarting: uiState.isRestarting
                )
                .onTapGesture { onClose() }
                .frame(maxHeight: .infinity, alignment: .top)
                .padding(.top, 73)
                CenterProgress(
                    progress: $progress,
                    label: uiState.elapsedLabel(),
                    isCountingDown: uiState.isCountingDown,
                    isRestarting: uiState.isRestarting,
                    countDownAnimation: countDownAnimation,
                    restartAnimation: restartAnimation,
                    stopAnimation: stopAnimation
                )
                .frame(maxHeight: .infinity)
                .padding(.top, 12)
                BottomButtons(
                    value: uiState.value,
                    isCountingDown: uiState.isCountingDown,
                    isRestarting: uiState.isRestarting,
                    isStopped: uiState.isStopped(),
                    onStart: onStart,
                    onPause: onPause,
                    onReset: onReset,
                    onStop:  onStop
                )
                .frame(maxHeight: .infinity, alignment: .bottom)
                .padding(.bottom, 73)
            }
            .frame(maxHeight: .infinity)
            TimeDragOverlay
        }
        .onAppear {
            //when restoring state
            progress = uiState.progress / 100
        }
        .onChange(of: uiState) { new in
            progress = new.progress / 100
        }
        .gesture(timeDrag)
    }
    
    private var TimeDragOverlay: some View {
        ZStack {
            Color.black.opacity(0.85)
            HStack(alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/) {
                Text(UtilsKt.padTime(time: minutes))
                    .font(Font.custom(FontResources.whosNext, size: 57))
                    .foregroundColor(Color.white)
                    .opacity(isDraggingLeft ? 1 : 0.5)
                    .padding(.trailing, -8)
                Text(":")
                    .font(Font.custom(FontResources.whosNext, size: 57))
                    .foregroundColor(Color.white)
                Text(UtilsKt.padTime(time: seconds))
                    .font(Font.custom(FontResources.whosNext, size: 57))
                    .foregroundColor(Color.white)
                    .opacity(isDraggingLeft ? 0.5 : 1)
                    .padding(.leading, -8)
            }
            .padding(.top, 35)
            HStack(alignment: .bottom) {
                VStack(alignment: .center) {
                    Image(ImageRecources.icSetterGesture)
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(Color.white)
                        .rotation3DEffect(.degrees(180), axis: (0,1,0))
                    Text(StringResources.lblMinutes.uppercased())
                        .font(Font.custom(FontResources.helveticaNeueLight, size: 16))
                        .foregroundColor(Color.white)
                        .frame(width: 120, alignment: .leading)
                }
                .opacity(isDraggingLeft ? 1 : 0.5)
                Image(ImageRecources.icSetter)
                    .resizable()
                    .scaledToFit()
                    .foregroundColor(Color.white)
                    .frame(height: 120)
                    .padding(.horizontal, 20)
                    .padding(.bottom, 50)
                VStack(alignment: .center) {
                    Image(ImageRecources.icSetterGesture)
                        .resizable()
                        .scaledToFit()
                        .foregroundColor(Color.white)
                    Text(StringResources.lblSeconds.uppercased())
                        .font(Font.custom(FontResources.helveticaNeueLight, size: 16))
                        .foregroundColor(Color.white)
                        .frame(width: 120, alignment: .trailing)
                }
                .opacity(isDraggingLeft ? 0.5 : 1)
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .bottom)
            .padding(.bottom, 30)
            .padding(.horizontal, 30)
        }
        .opacity(uiState.isSettingTimer ? 1 : 0)
        .animation(.linear, value: uiState.isSettingTimer)
    }
}

#Preview {
    let animation : Animation = Animation.linear(duration: 0.25)
    return Timer(
        uiState: TimerUiState(value: 0, elapsed: 0.0, progress: 0.0, backgroundIndex: 0, isSettingTimer: false, isCountingDown: false, isRestarting: true),
        countDownAnimation: animation,
        restartAnimation: animation,
        stopAnimation: animation,
        backgroundColor: ColorsPallete.green,
        onClose: {},
        onStart: {},
        onPause: {},
        onReset: {},
        onStop: {},
        onSettingTime: {},
        onSetTime: { Int32 in }
    )
    .ignoresSafeArea()    
}

#Preview {
    let animation : Animation = Animation.linear(duration: 0.25)
    return Timer(
        uiState: TimerUiState(value: 0, elapsed: 0.0, progress: 0.0, backgroundIndex: 0, isSettingTimer: true, isCountingDown: false, isRestarting: true),
        countDownAnimation: animation,
        restartAnimation: animation,
        stopAnimation: animation,
        backgroundColor: ColorsPallete.green,
        onClose: {},
        onStart: {},
        onPause: {},
        onReset: {},
        onStop: {},
        onSettingTime: {},
        onSetTime: { Int32 in }
    )
    .ignoresSafeArea()
}
