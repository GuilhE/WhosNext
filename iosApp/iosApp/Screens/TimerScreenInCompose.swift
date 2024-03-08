import SwiftUI
import WhosNextComposables

struct TimerScreenInCompose: View {
    let onClose: () -> Void
    
    var body: some View {
        ZStack {
            TimerScreenInComposeRepresentable()
            Button(action: { onClose() }, label: { Color.clear })
                .frame(width: 200, height: 200, alignment: .top)
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        }
    }
}

public struct TimerScreenInComposeRepresentable: UIViewControllerRepresentable {
    
    public func makeUIViewController(context: Context) -> UIViewController {
        TimerScreenInComposeUIViewController().make()
    }
    
    public func updateUIViewController(_ uiViewController: UIViewController, context: Context) { }
}

#Preview {
    TimerScreenInCompose(onClose: {})
        .ignoresSafeArea()
}
