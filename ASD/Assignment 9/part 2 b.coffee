@startuml

User -> CalendarApp: login(username, password)
CalendarApp -> User: Authenticate user
User -> CalendarApp: createAppointment(appointmentData)
CalendarApp -> MonthlyView: create()
CalendarApp -> WeeklyView: create()
CalendarApp -> Calender: addObserver(MonthlyView)
CalendarApp -> Calender: addObserver(WeeklyView)
CalendarApp -> CreateAppointmentCommand: execute()
CreateAppointmentCommand -> Calendar: addAppointment(appointmentData)
CalendarApp -> CommandHistory: AddCommand(Command)
Calendar -> MonthlyView: notify(appointmentData)
MonthlyView -> MonthlyView: display()
Calendar -> WeeklyView: notify(appointmentData)
WeeklyView -> WeeklyView: display()


User -> CalendarApp: undo()
CalendarApp -> CommandHistory: undo()
CommandHistory -> CreateAppointmentCommand: unexecute()
CreateAppointmentCommand -> Calendar: removeAppointment(appointmentData)
Calendar -> MonthlyView: notify(appointmentData)
MonthlyView -> MonthlyView: display()
Calendar -> WeeklyView: notify(appointmentData)
WeeklyView -> WeeklyView: display()


User -> CalendarApp: redo()
CalendarApp -> CommandHistory: redo()
CommandHistory -> CreateAppointmentCommand: execute()
CreateAppointmentCommand -> Calendar: createAppointment()
Calendar -> MonthlyView: notify(appointmentData)
MonthlyView -> MonthlyView: display()
Calendar -> WeeklyView: notify(appointmentData)
WeeklyView -> WeeklyView: display()

@enduml
