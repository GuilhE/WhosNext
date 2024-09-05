import SwiftUI
import WhosNextShared

@main
struct WhosNextApp: App {
    init() {
        DependencyInjection().doInitKoin { _ in }
    }

    var body: some Scene {
        WindowGroup {
            MainScreen()
            // .onAppear() { FontResources.printFontNames() }
        }
    }
}
