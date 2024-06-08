import SwiftUI

struct MainScreen: View {
    @State private var showSwiftTimer = false
    @State private var showComposeTimer = false
    @State private var animateIntro = true
    
    var body: some View {
        ZStack {
            if(!showSwiftTimer && !showComposeTimer) {
                SplashScreen(
                    animate: animateIntro,
                    onSwift: { showSwiftTimer = true },
                    onCompose: { showComposeTimer = true }
                )
                .transition(.opacity.animation(.linear(duration: 0.5)))
            }
            
            if(showSwiftTimer) {
                TimerScreenInSwift(
                    onClose: {
                        animateIntro = false
                        showSwiftTimer = false
                    }
                )
                .transition(.opacity.animation(.linear(duration: 0.5)))
            }
            
            if(showComposeTimer) {
                TimerScreenInCompose(
                    onClose: {
                        animateIntro = false
                        showComposeTimer = false
                    }
                )
                .transition(.opacity.animation(.linear(duration: 0.5)))
            }
        }
        .ignoresSafeArea()
    }
}

#Preview {
    MainScreen()
}
