import SwiftUI
import WhosNextShared

struct CenterProgress: View {
    @Binding var progress: Float
    let label: String
    let isCountingDown: Bool
    let isRestarting: Bool
    let countDownAnimation: Animation
    let restartAnimation: Animation
    let stopAnimation: Animation
    
    var body: some View {
        ZStack{
            Image(ImageRecources.bgWatch)
                .resizable()
            CircularProgress(
                progress: $progress,
                animation: isRestarting ? restartAnimation : isCountingDown ? countDownAnimation : stopAnimation
            )
            .frame(width: 240)
            VStack(alignment: .center) {
                Image(ImageRecources.icWatch)
                    .foregroundColor(.white)
                    .padding(.bottom, 10)
                Text(label)
                    .font(Font.custom(FontResources.whosNext, size: 57))
                    .foregroundColor(Color.white)
                    .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, alignment: .center)
            }
            .padding(.bottom, 40)
        }
        .frame(width: 250, height: 250)
    }
}

struct CenterProgress_Previews: PreviewProvider {
    static var previews: some View {
        @State var progress : Float = 0.3
        let animation : Animation = Animation.linear(duration: 0.25)
        
        ZStack {
            ColorsPallete.green
            VStack {
                CenterProgress(progress: $progress, label: "22:14", isCountingDown: true, isRestarting: false, countDownAnimation: animation, restartAnimation: animation, stopAnimation: animation)
            }
        }
        .ignoresSafeArea()
    }
}

/*
#Preview {
    @Previewable @State var progress : Float = 0.3
    let animation : Animation = Animation.linear(duration: 0.25)
    return ZStack {
        ColorsPallete.green
        VStack {
            CenterProgress(progress: $progress, label: "22:14", isCountingDown: true, isRestarting: false, countDownAnimation: animation, restartAnimation: animation, stopAnimation: animation)
        }
    }
    .ignoresSafeArea()
}
*/
