import SwiftUI

struct TimerScreenInCompose: View {
    let onClose: () -> Void
    
    var body: some View {
        ZStack {
            TimerScreenInComposeRepresentable()
            Button(action: { onClose() }, label: { Color.white.opacity(0.0000001) })
                .frame(width: 200, height: 200, alignment: .top)
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        }
    }
}

#Preview {
    TimerScreenInCompose(onClose: {})
        .ignoresSafeArea()
}
