import SwiftUI

struct BottomButtons: View {
    let value: Int32
    let isCountingDown: Bool
    let isRestarting: Bool
    let isStopped: Bool
    let onStart: () -> Void
    let onPause: () -> Void
    let onReset: () -> Void
    let onStop: () -> Void

    var body: some View {
        HStack(alignment: /*@START_MENU_TOKEN@*/ .center/*@END_MENU_TOKEN@*/, spacing: 40) {
            Button(action: { isCountingDown == true ? onPause() : onStart() }) {
                Image(isCountingDown ? ImageRecources.btPause : ImageRecources.btPlay)
                    .resizable()
                    .scaledToFit()
            }
            .disabled(value == 0 || isRestarting)
            .opacity(value > 0 && !isRestarting ? 1 : 0.5)
            Button(action: { isStopped == true ? onReset() : onStop() }) {
                Image(ImageRecources.btReset)
                    .resizable()
                    .scaledToFit()
            }
            .disabled(isRestarting)
            .opacity(!isRestarting ? 1 : 0.5)
        }
        .frame(height: 120)
    }
}

#Preview {
    ZStack {
        ColorsPallete.green
        VStack {
            BottomButtons(value: 60, isCountingDown: false, isRestarting: false, isStopped: true, onStart: {}, onPause: {}, onReset: {}, onStop: {})
            BottomButtons(value: 60, isCountingDown: true, isRestarting: false, isStopped: false, onStart: {}, onPause: {}, onReset: {}, onStop: {})
            BottomButtons(value: 60, isCountingDown: false, isRestarting: true, isStopped: true, onStart: {}, onPause: {}, onReset: {}, onStop: {})
        }
    }
    .ignoresSafeArea()
}
