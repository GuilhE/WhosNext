import AVFoundation
import SwiftUI

enum StringResources {
    static let lblTeams = "Choose your Team"
    static let lblMinutes = "Minutes"
    static let lblSeconds = "Seconds"
}

enum ImageRecources {
    static let bgTexture = "bg_texture"
    static let bgWatch = "bg_watch"
    static let icSwift = "ic_swift"
    static let icCompose = "ic_compose"
    static let icLogo = "ic_logo"
    static let icLogoAlt = "ic_logo_alt"
    static let icZone = "ic_zone"
    static let icChronoBall = "ic_chrono_ball"
    static let icWatch = "ic_watch"
    static let icSetterGesture = "ic_setter_gesture"
    static let icSetter = "ic_setter"
    static let btPause = "bt_pause"
    static let btPlay = "bt_play"
    static let btReset = "bt_reset"
}

enum FontResources {
    static let helveticaNeueLight = "HelveticaNeue-Light"
    static let whosNext = "WhosNext-LightItalic"

    static func printFontNames() {
        for family: String in UIFont.familyNames {
            print(family)
            for names: String in UIFont.fontNames(forFamilyName: family) {
                print("== \(names)")
            }
        }
    }
}

enum AudioResources {
    static let timesUp = "timesup"
}

enum ColorsPallete {
    static let green = Color("green")
    static let greenAlt = Color("greenAlt")
    static let whiteGhost = Color("whiteGhost")
    static let blue = Color("blue")
    static let pink = Color("pink")
    static let yellow = Color("yellow")
    static let orange = Color("orange")
    static let purple = Color("purple")

    static func toColor(index: Int32) -> Color {
        switch index {
        case 1: return ColorsPallete.blue
        case 2: return ColorsPallete.pink
        case 3: return ColorsPallete.yellow
        case 4: return ColorsPallete.orange
        case 5: return ColorsPallete.purple
        default: return ColorsPallete.green
        }
    }
}

struct TimesUpSoundPlayer {
    private var audioPlayer: AVAudioPlayer?

    init() {
        guard let soundURL = Bundle.main.url(forResource: AudioResources.timesUp, withExtension: "mp3") else {
            fatalError("Unable to find \(AudioResources.timesUp) in bundle")
        }
        do {
            audioPlayer = try AVAudioPlayer(contentsOf: soundURL)
        } catch {
            print(error.localizedDescription)
        }
    }

    func play() {
        audioPlayer.map { player in
            if !player.isPlaying {
                player.play()
            }
        }
    }
}

enum EasingFunctions {
    static func linearOutSlowInEasing(duration: Double) -> Animation {
        return Animation.timingCurve(0.0, 0.0, 0.2, 1.0, duration: duration)
    }

    static func fastOutSlowInEasing(duration: Double) -> Animation {
        return Animation.timingCurve(0.4, 0.0, 0.2, 1.0, duration: duration)
    }
}
