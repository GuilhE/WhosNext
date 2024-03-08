import SwiftUI

struct HeaderLogo: View {
    let isRestarting: Bool
    private let chronoAnimation = EasingFunctions.fastOutSlowInEasing(duration: 0.05).repeatForever(autoreverses: true)
    
    var body: some View {
        ZStack {
            Image(ImageRecources.icLogoAlt)
                .foregroundColor(.white)
            Image(ImageRecources.icChronoBall)
                .foregroundColor(.white)
                .rotationEffect(Angle(degrees: isRestarting ? -30 : 0.0))
                .offset(x: 30, y: -25)
                .animation(isRestarting ? chronoAnimation : .default, value: isRestarting)
        }
    }
}

#Preview {
    ZStack {
        ColorsPallete.green
        VStack {
            HeaderLogo(isRestarting: false)
            HeaderLogo(isRestarting: true)
        }
    }
    .ignoresSafeArea()
}
