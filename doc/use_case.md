# UseCase for Minpaku
```plantuml
left to right direction
skinparam actorStyle awesome

together {
  :Guest: as g
  :User: as u
  :Admin: as a
}

package Authentication {
  (Register user) as register
  (Login) as login
  (Logout) as logout
}

g --> register
u --> login
a --> login
u --> logout
a --> logout

package Reservation {
  (See available rooms) as see
  (Book rooms) as book
  (Confirm reservation) as confirm
}
g --> see
u --> see
a --> see
u --> book
g --> book
u --> confirm
a --> confirm

package AdminTool {
  (See monthly revenue) as rev
  (See reservation detail) as detail
}
a --> rev
a --> detail
```