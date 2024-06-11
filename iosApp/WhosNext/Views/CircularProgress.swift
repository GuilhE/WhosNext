import SwiftUI

struct CircularProgress: View {
    @Binding var progress: Float
    let animation: Animation
    private let strokeStyle = StrokeStyle(lineWidth: 35.0, lineCap: .round, lineJoin: .round)
    private let rotation = Angle(degrees: 270.0)
    
    var body: some View {
        ZStack {
            Group {
                Circle()
                    .trim(from: 0.0, to: CGFloat(min(self.progress, 1.0)))
                    .stroke(style: strokeStyle)
                    .opacity(0.2)
                    .foregroundColor(Color.black)
                    .rotationEffect(rotation)
                    .offset(x: 0, y: 15)
                Circle()
                    .trim(from: 0.0, to: CGFloat(min(self.progress, 1.0)))
                    .stroke(style: strokeStyle)
                    .opacity(1)
                    .foregroundColor(Color.white)
                    .rotationEffect(rotation)
            }
        }
        .animation(animation, value: progress)
    }
}

struct CircularProgress_Previews: PreviewProvider {
    static var previews: some View {
        OtherView(progress: 0.6)
    }
    
    struct OtherView : View {
        @State var progress : Float = 0.0
        var animation : Animation = Animation.linear(duration: 0.25)
        
        var body: some View {
            ZStack {
                Color.yellow
                VStack {
                    CircularProgress(progress: self.$progress, animation: self.animation)
                        .padding(50)
                    Button(action: {
                        if(progress >= 1) {
                            progress = 0
                        } else {
                            progress += 0.1
                        }
                    }) {
                        Text("try me")
                            .frame(width: 200, height: 50)
                            .overlay(
                                RoundedRectangle(cornerRadius: 20)
                                    .stroke(Color.blue, lineWidth: 2)
                            )
                            .padding(.bottom, 0)
                        
                    }
                }
            }.ignoresSafeArea()
        }
    }
}

/*
 #Preview {
    @Previewable @State var progress : Float = 0.0
    let animation : Animation = Animation.linear(duration: 0.25)
    return ZStack {
        Color.yellow
        VStack {
            CircularProgress(progress: $progress, animation: animation)
                .padding(50)
            Button(action: {
                if(progress >= 1) {
                    progress = 0
                } else {
                    progress += 0.1
                }
            }) {
                Text("try me")
                    .frame(width: 200, height: 50)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(Color.blue, lineWidth: 2)
                    )
                    .padding(.bottom, 0)
                
            }
        }
    }.ignoresSafeArea()
}
*/
