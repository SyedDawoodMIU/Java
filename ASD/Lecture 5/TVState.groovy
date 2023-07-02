@startuml

interface TVState {
    + power() : TVState
}

interface TVInteractiveState {
    + volumeUp() : void
    + volumeDown() : void
    + channelUp() : void
    + channelDown() : void
    + numericKeyPressed(key: int) : void
    + settingsButtonPressed() : void
    + playButtonPressed() : void
    + forwardButtonPressed() : void
}

class OffState implements TVState {
    + power() : TVState
}

class OnState implements TVState, TVInteractiveState {
    + power() : TVState
    + volumeUp() : void
    + volumeDown() : void
    + channelUp() : void
    + channelDown() : void
    + numericKeyPressed(key: int) : void
    + settingsButtonPressed() : void
    + playButtonPressed() : void
    + forwardButtonPressed() : void
}

class NumericKeyState implements TVInteractiveState {
    + volumeUp() : void
    + volumeDown() : void
    + channelUp() : void
    + channelDown() : void
    + numericKeyPressed(key: int) : void
}

class SettingsState implements TVInteractiveState {
    + volumeUp() : void
    + volumeDown() : void
    + channelUp() : void
    + channelDown() : void
    + settingsButtonPressed() : void
}

class PlayState implements TVInteractiveState {
    + volumeUp() : void
    + volumeDown() : void
    + channelUp() : void
    + channelDown() : void
    + playButtonPressed() : void
    + forwardButtonPressed() : void
}

class VolumeState implements TVInteractiveState {
    + volumeUp() : void
    + volumeDown() : void
    + channelUp() : void
    + channelDown() : void
}

class ChannelState implements TVInteractiveState {
    + volumeUp() : void
    + volumeDown() : void
    + channelUp() : void
    + channelDown() : void
}

class TVContext {
    - currentState: TVState
    + power() : void
    + setState(state: TVState) : void
}

class RemoteControl {
    - tvContext: TVContext
    + powerButtonPressed() : void
    + volumeUpButtonPressed() : void
    + volumeDownButtonPressed() : void
    + channelUpButtonPressed() : void
    + channelDownButtonPressed() : void
    + numericKeyPressed(key: int) : void
    + settingsButtonPressed() : void
    + playButtonPressed() : void
    + forwardButtonPressed() : void
}

// TVContext o-- TVState
// TVState <|.. TVInteractiveState
// RemoteControl o-- TVContext

// TVState <|-- OffState
// TVState <|-- OnState
// TVInteractiveState <|-- OnState
// TVInteractiveState <|-- NumericKeyState
// TVInteractiveState <|-- SettingsState
// TVInteractiveState <|-- PlayState
// TVInteractiveState <|-- VolumeState
// TVInteractiveState <|-- ChannelState

@enduml
