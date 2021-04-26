```plantuml
' hide the spot
hide circle

' avoid problems with angled crows feet
skinparam linetype ortho

entity "User" as user {
  *user_id : number <<generated>>
  --
  name : text
  *permission_id: number
}

entity "Permission" as per {
  *permission_id : number <<generated>>
  --
  name : text
  description : text
}

entity "Profile" as pro {
  *user_id : number <<generated>>
  --
  name : text
  password: password
  email: text
  address: text
  phone_number: text
}

user |o..|{ per
user |o..|{ pro

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