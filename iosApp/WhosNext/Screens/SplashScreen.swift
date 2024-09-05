import SwiftUI

struct SplashScreen: View {
    let animate: Bool
    let onSwift: () -> Void
    let onCompose: () -> Void

    @State private var showTeams = false

    var body: some View {
        ZStack {
            ColorsPallete.greenAlt
            Image(ImageRecources.bgTexture)
                .resizable()
            VStack {
                Image(ImageRecources.icZone)
                    .resizable()
                    .scaledToFit()
                    .frame(height: 170, alignment: .top)
                    .rotationEffect(.degrees(180))
                    .foregroundColor(.white)
                if !animate || showTeams {
                    Teams
                } else {
                    Image(ImageRecources.icLogo)
                        .frame(maxHeight: .infinity, alignment: .center)
                        .foregroundColor(.white)
                        .transition(.opacity.animation(.linear(duration: 0.3)))
                }
                Image(ImageRecources.icZone)
                    .resizable()
                    .scaledToFit()
                    .frame(height: 170, alignment: .bottom)
                    .foregroundColor(.white)
            }
        }
        .task {
            do {
                try await Task.sleep(nanoseconds: animate == true ? 1_000_000_000 : 0_000_000_000)
                showTeams.toggle()
            } catch {
                showTeams.toggle()
            }
        }
    }

    @State private var textOpacity: Double = 0
    @State private var firstImageOffset: CGFloat = -150
    @State private var secondImageOffset: CGFloat = 150

    private var Teams: some View {
        VStack {
            HStack {
                Image(ImageRecources.icSwift)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 100)
                    .foregroundColor(.white)
                    .padding()
                    .onTapGesture { onSwift() }
                    .offset(x: firstImageOffset)

                Image(ImageRecources.icCompose)
                    .resizable()
                    .foregroundColor(.white)
                    .scaledToFit()
                    .frame(width: 90)
                    .padding()
                    .onTapGesture { onCompose() }
                    .offset(x: secondImageOffset)
            }

            Text(StringResources.lblTeams)
                .font(Font.custom(FontResources.helveticaNeueLight, size: 25).italic())
                .foregroundColor(Color.white)
                .padding()
                .opacity(textOpacity)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        .padding()
        .onAppear {
            if animate {
                withAnimation(Animation.interpolatingSpring(stiffness: 150, damping: 18, initialVelocity: 0)) {
                    textOpacity = 1
                    firstImageOffset = 0
                    secondImageOffset = 0
                }
            } else {
                textOpacity = 1
                firstImageOffset = 0
                secondImageOffset = 0
            }
        }
    }
}

#Preview {
    SplashScreen(animate: true, onSwift: {}, onCompose: {})
        .ignoresSafeArea()
}
