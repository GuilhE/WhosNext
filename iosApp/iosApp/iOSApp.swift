import SwiftUI
import WhosNextShared

@main
struct iOSApp: App {

    init() {
        DependencyInjection().doInitKoin { _ in }
    }

    var body: some Scene {
        WindowGroup {
            MainScreen()
                //.onAppear() { FontResources.printFontNames() }
        }
    }
}
