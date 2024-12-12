    package com.hieuphinehehe.backend.controller;

    import com.hieuphinehehe.backend.dto.response.AppointmentResponse;
    import com.hieuphinehehe.backend.mapper.AppointmentMapper;
    import com.hieuphinehehe.backend.model.Appointment;
    import com.hieuphinehehe.backend.model.Member;
    import com.hieuphinehehe.backend.dto.request.appointment.AddAppointmentRequest;
    import com.hieuphinehehe.backend.dto.request.appointment.UpdateAppointmentRequest;
    import com.hieuphinehehe.backend.dto.response.ApiResponse;
    import com.hieuphinehehe.backend.service.AppointmentService;
    import com.hieuphinehehe.backend.service.MemberService;
    import com.hieuphinehehe.backend.utils.CustomPagination;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.domain.Page;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/appointments")
    @RequiredArgsConstructor
    @Slf4j
    public class AppointmentController {

        private final AppointmentService appointmentService;
        private final MemberService memberService;
        private final AppointmentMapper appointmentMapper;

        @PostMapping
        public ResponseEntity<ApiResponse<?>> addAppointment(@Valid @RequestBody AddAppointmentRequest addAppointmentRequest) {
            Member member = memberService.getMemberById(addAppointmentRequest.getMemberId());
            Appointment appointment = Appointment.builder()
                    .member(member)
                    .doctor(addAppointmentRequest.getDoctor())
                    .time(addAppointmentRequest.getTime())
                    .location(addAppointmentRequest.getLocation())
                    .build();
            Appointment createdAppointment = appointmentService.addAppointment(appointment);
            ApiResponse<Appointment> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Add appointment successfully",
                    createdAppointment
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<?>> updateAppointment(
                @PathVariable("id") Integer id,
                @Valid @RequestBody UpdateAppointmentRequest updateAppointmentRequest) {
            Member member = memberService.getMemberById(updateAppointmentRequest.getMemberId());
            Appointment appointment = Appointment.builder()
                    .id(id)
                    .member(member)
                    .doctor(updateAppointmentRequest.getDoctor())
                    .time(updateAppointmentRequest.getTime())
                    .location(updateAppointmentRequest.getLocation())
                    .build();
            Appointment updatedAppointment = appointmentService.updateAppointment(appointment);
            ApiResponse<Appointment> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Update appointment successfully",
                    updatedAppointment
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<?>> deleteAppointment(@PathVariable("id") Integer id) {
            appointmentService.deleteAppointment(id);
            ApiResponse<Appointment> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Delete appointment successfully",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("")
        public ResponseEntity<ApiResponse<CustomPagination<AppointmentResponse>>> getAllAppointments(
                @RequestParam(name = "page", defaultValue = "1") int page,
                @RequestParam(name = "size", defaultValue = "10") int size,
                @RequestParam(name = "keyword", required = false) String keyword,
                @RequestParam(required = false) Long memberId) {
            Page<Appointment> appointments = appointmentService.getAllAppointments(page, size, keyword);
            Page<AppointmentResponse> appointmentsList = appointmentMapper.toAppointmentsResponse(appointments);
            CustomPagination<AppointmentResponse> appointmentResponseCustomPagination = new CustomPagination<AppointmentResponse>(appointmentsList);
            ApiResponse<CustomPagination<AppointmentResponse> > response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Get all appointments successfully",
                    appointmentResponseCustomPagination
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
