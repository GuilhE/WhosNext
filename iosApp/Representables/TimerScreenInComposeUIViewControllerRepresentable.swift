import SwiftUI
import WhosNextComposables

public struct TimerScreenInComposeRepresentable: UIViewControllerRepresentable {
    public func makeUIViewController(context _: Context) -> UIViewController {
        TimerScreenInComposeUIViewController().make()
    }

    public func updateUIViewController(_: UIViewController, context _: Context) {
        // unused
    }
}
