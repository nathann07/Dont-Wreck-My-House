## Project Architecture
#### This file outlines the full architecture of the Don’t Wreck My House application, including key classes, methods, and relationships across the domain, service, data, and UI layers.
#### Headings represent directories/packages, bullet points represent files, fields, and methods.
## Data
* guests.csv (holds all production guests)
* guests-seed.csv (holds seed guest data for testing)
* guests-test.csv (guest data used for testing)
* hosts.csv (holds all production guests)
* hosts-seed.csv (holds seed host data for testing)
* hosts-test.csv (host data used for testing)

### Reservations (Holds all production reservation files)
* Many reservation files
### Reservations_test (holds all test reservation files)
* reservation-seed.csv (holds seed reservation data for testing)
* reservation-test.csv (reservation data used for testing)

## src-main-resources:
### Resources
* data.properties (for Spring DI with annotations)
  * guestFilePath=./data/guests.csv 
  * hostFilePath=./data/hosts.csv 
  * reservationDirectory=./data/reservations

## src-main-java-learn-mastery:
### Models
* Guest
  * int id
  * String firstName
  * String lastName
  * String email
  * String phone
  * String state
  *
  * int getId()
  * void setId(int id)
  * String getFirstName()
  * void setFirstName(String firstName)
  * String getLastName()
  * void setLastName(String lastName)
  * String getEmail()
  * void setEmail(String email)
  * String getPhone()
  * void setPhone(String phone)
  * String getState()
  * void setState(String state)
  * boolean equals(Object o)
  * int hashCode()
* Host
  * String id
  * String lastName
  * String email
  * String phone
  * String address
  * String city
  * String state
  * int postalCode
  * BigDecimal standardRate
  * BigDecimal weekendRate
  * 
  * String getId()
  * void setId(String id)
  * String getLastName()
  * void setLastName(String lastName)
  * String getEmail()
  * void setEmail(String email)
  * String getPhone()
  * void setPhone(String phone)
  * String getAddress()
  * void setAddress(String address)
  * String getCity()
  * void setCity(String city)
  * String getState()
  * void setState(String state)
  * int getPostalCode()
  * void setPostalCode(int postalCode)
  * BigDecimal getStandardRate()
  * void setStandardRate(BigDecimal standardRate)
  * BigDecimal getWeekendRate()
  * void setWeekendRate(BigDecimal weekendRate)
  * boolean equals(Object o)
  * int hashCode()
* Reservation
  * int id
  * LocalDate startDate
  * LocalDate endDate
  * Guest guest
  * Host host
  * BigDecimal total
  * 
  * int getId()
  * void setId(int id)
  * LocalDate getStartDate()
  * void setStartDate(LocalDate startDate)
  * LocalDate getEndDate()
  * void setEndDate(LocalDate endDate)
  * Guest getGuest()
  * void setGuest(Guest guest)
  * Host getHost()
  * void setHost(Host host)
  * BigDecimal getTotal()
  * void setTotal(BigDecimal total)
  * BigDecimal calculateTotal()
  * boolean equals(Object o)
  * int hashCode()

### Data
* DataException
  * DataException(String message)
  * DataException(Throwable cause)
  * DataException(String message, Throwable cause)
* GuestRepository
  * Guest findByEmail(String email)
  * Guest findById(int id);
* GuestFileRepository
  * String DELIMITER
  * String filePath
  *
  * List\<Guest> findAll()
  * Guest findByEmail(String email)
  * Guest findById(int id);
  * Guest deserialize(String[] fields)
* HostRepository
  * Host findByEmail(String email)
* HostFileRepository
  * String DELIMITER
  * String filePath
  *
  * List\<Host> findAll()
  * Host findByEmail(String email)
  * Host deserialize(String[] fields)
* ReservationRepository
  * List\<Reservation> findByHost(Host host)
  * List\<Reservation> findByBoth(Host host, Guest guest)
  * Reservation add(Reservation reservation)
  * boolean update(Reservation reservation)
  * boolean delete(Reservation reservation)
* ReservationFileRepository
  * String DELIMITER
  * String HEADER
  * String directory
  * 
  * List\<Reservation> findByHost(Host host)
  * List\<Reservation> findByBoth(Host host, Guest guest)
  * String getFilePath(Host host)
  * Reservation add(Reservation reservation)
  * boolean update(Reservation reservation)
  * boolean delete(Reservation reservation)
  * void writeAll(List\<Reservation> reservations, Host host)
  * String serialize(Reservation reservation)
  * Reservation deserialize(String[] fields, Host host)

### Domain
* GuestService
  * GuestRepository repository
  * 
  * Guest findByEmail(String email)
* HostService
  * HostRepository repository
  * 
  * Host findByEmail(String email)
* ReservationService
  * ReservationRepository reservationRepository 
  * GuestRepository guestRepository 
  * HostRepository hostRepository
  *
  * List\<Reservation> findByHost(Host host)
  * List\<Reservation> findByBoth(Host host, Guest guest)
  * Result\<Reservation> add(Reservation reservation)
  * Result\<Reservation> update(Reservation reservation)
  * Result\<Reservation> delete(Reservation reservation)
  * Result\<Reservation> validate(Reservation reservation)
* Response
  * ArrayList\<String> messages
  * 
  * boolean isSuccess()
  * List\<String> getErrorMessages()
  * void addErrorMessage(String message)
* Result
  * T payload
  * T getPayload()
  * void setPayload(T payload)

### UI
* Controller
  * GuestService guestService
  * HostService hostService
  * ReservationService reservationService
  * View view
  * 
  * void run()
  * void runMenuLoop()
  * void viewReservations()
  * void addReservation()
  * void editReservation()
  * void deleteReservation()
* View
  * ConsoleIO io
  * DateTimeFormatter formatter
  * 
  * MainMenuOption selectMainMenuOption()
  * Reservation chooseReservation(List\<Reservation> reservations, boolean onlyFutureReservations)
  * Reservation makeReservation(Host host, Guest guest)
  * Reservation updateReservation(Reservation reservation)
  * String getHostEmail()
  * String getGuestEmail()
  * void displayReservations(List<Reservations> reservations)
  * void displayHeader(String message)
  * void displayException(Exception ex)
  * void displayStatus(boolean success, String message)
  * void displayStatus(boolean success, List\<String> messages)
  * void displaySuccessMessage(boolean success, String message)
  * boolean displaySummary(Reservation reservation)
* ConsoleIO
  * Scanner scanner
  * DateTimeFormatter formatter
  * 
  * void print(String message)
  * void println(String message)
  * void printf(String format, Object... values)
  * String readString(String prompt)
  * String readRequiredString(String prompt)
  * int readInt(String prompt)
  * int readInt(String prompt, int min, int max)
  * boolean readBoolean(String prompt)
  * LocalDate readLocalDate(String prompt)
  * LocalDate readOptionalLocalDate(String prompt, LocalDate defaultValue)
* MainMenuOption
  * EXIT(0, "Exit")
  * VIEW_RESERVATIONS(1, "View Reservations")
  * ADD_A_RESERVATION(2, "Add a Reservation")
  * EDIT_A_RESERVATION(3, "Edit a Reservation")
  * DELETE_A_RESERVATION(4, "Delete a Reservation")
  * 
  * int value
  * String message
  * 
  * MainMenuOption fromValue(int value)
  * int getValue()
  * String getMessage()
### App.java
  * ApplicationContext context
  * Controller controller

## src-test-learn-mastery:

### Data
* GuestRepositoryDouble
  * List\<Guest> findAll()
  * Guest findByEmail(String email)
  * Guest findById(int id)
* GuestFileRepositoryTest
  * String SEED_PATH
  * String TEST_PATH
  * GuestFileRepository repository
  *
  * void setup()
  * void shouldFindByEmail()
  * void shouldFindById()
* HostRepositoryDouble
  * List\<Host> findAll()
  * Host findByEmail(String email)
* HostFileRepositoryTest
  * String SEED_PATH
  * String TEST_PATH
  * HostFileRepository repository
  *
  * void setup()
  * void shouldFindByEmail()
* ReservationRepositoryDouble
  * List\<Reservation> findByHost(Host host)
  * List\<Reservation> findByBoth(Host host, Guest guest)
  * Reservation add(Reservation reservation)
  * boolean update(Reservation reservation)
  * boolean delete(Reservation reservation)
* ReservationFileRepositoryTest
  * String SEED_PATH
  * String TEST_PATH
  * String TEST_DIR_PATH
  * Host host
  * ReservationFileRepository repository
  *
  * void setup()
  * void shouldAddReservation()
  * void shouldFindReservationByHost()
  * void shouldFindReservationByBoth()
  * void shouldUpdateReservation()
  * void shouldNotUpdateNonExistingReservation()
  * void shouldDeleteReservation()
  * void shouldNotDeleteNonExistingReservation()

### Domain
* GuestServiceTest
  * GuestRepositoryDouble repository
  * GuestService service
  * 
  * void shouldFindByEmail()
  * void shouldNotFindByNullEmail()
  * void shouldNotFindByNonExistingEmail()
* HostServiceTest
  * HostRepositoryDouble repository
  * HostService service
  * 
  * void shouldFindByEmail()
  * void shouldNotFindByNullEmail()
  * void shouldNotFindByNonExistingEmail()
* ReservationServiceTest
  * ReservationRepositoryDouble reservationRepository
  * GuestRepositoryDouble guestRepository
  * HostRepositoryDouble hostRepository
  * ReservationService service
  * Host host
  * 
  * void setup()
  * void shouldFindByHost()
  * void shouldNotFindByNullHost()
  * void shouldFindByBoth()
  * void shouldNotFindByNullBoth()
  * void shouldAdd()
  * void shouldNotAddNullHost()
  * void shouldNotAddNullGuest()
  * void shouldNotAddNullReservation()
  * void shouldNotAddNullStartDate()
  * void shouldNotAddNullEndDate()
  * void shouldNotAddInvalidStartDate()
  * void shouldNotAddOverlappingDate()
  * void shouldUpdate()
  * void shouldNotUpdateNonExistingReservation()
  * void shouldNotUpdateNullHost()
  * void shouldNotUpdateNullGuest()
  * void shouldNotUpdateNullReservation()
  * void shouldNotUpdateNullStartDate()
  * void shouldNotUpdateNullEndDate()
  * void shouldNotUpdateInvalidStartDate()
  * void shouldNotUpdateOverlappingDate()
  * void shouldDelete()
  * void shouldNotDeleteNonExistingReservation()
  * void shouldNotDeletePastReservation()
