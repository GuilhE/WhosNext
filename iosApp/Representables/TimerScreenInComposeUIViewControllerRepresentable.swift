import SwiftUI
import WhosNextComposables

public struct TimerScreenInComposeRepresentable: UIViewControllerRepresentable {
    
    public func makeUIViewController(context: Context) -> UIViewController {
        TimerScreenInComposeUIViewController().make()
    }

    public func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        //unused
    }
}