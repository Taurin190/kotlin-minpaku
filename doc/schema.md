```plantuml
' hide the spot
hide circle

' avoid problems with angled crows feet
skinparam linetype ortho

entity "User" as user {
  *user_id : number <<generated>>
  --
  name : text
  *permission: number
}

entity "Admin" as admin {
  *user_id : number <<generated>>
  --
  name : text
  password: password
  email: text
}

entity "Visitor" as vis {
  *user_id : number <<generated>>
  --
  name : text
  password: password
  email: text
  address: text
  phone_number: text
}

user |o..|{ admin
user |o..|{ vis

entity "Reservation" as res {
  *reservation_id : number <<generated>>
  --
  *user_id : number
  number_of_visitor: number
}

entity "Room" as room {
  *reservation_id : number <<generated>>
  *date : datetime <<generated>>
  --
  pay_id : number
}

entity "Payment" as pay {
  *pay_id : number <<generated>>
  --
  fee : number
}


user }|..o{ res
res }|..o| room
room ||..o| pay
```