    package com.hieuphinehehe.backend.controller;

    import com.hieuphinehehe.backend.mapper.HealthStatMapper;
    import com.hieuphinehehe.backend.model.HealthStat;
    import com.hieuphinehehe.backend.model.Member;
    import com.hieuphinehehe.backend.model.User;
    import com.hieuphinehehe.backend.dto.request.healthStat.AddHealthStatRequest;
    import com.hieuphinehehe.backend.dto.request.healthStat.UpdateHealthStatRequest;
    import com.hieuphinehehe.backend.dto.response.ApiResponse;
    import com.hieuphinehehe.backend.service.AuthenticationService;
    import com.hieuphinehehe.backend.service.HealthStatService;
    import com.hieuphinehehe.backend.service.MemberService;
    import jakarta.validation.Valid;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/v1/health-stats")
    @RequiredArgsConstructor
    @Slf4j
    public class HealthStatController {

        private final AuthenticationService authenticationService;
        private final HealthStatService healthStatService;
        private final MemberService memberService;

        @PostMapping
        public ResponseEntity<ApiResponse<?>> addHealthStat
                (@Valid @RequestBody AddHealthStatRequest addHealthStatRequest) {
            Member member = memberService.getMemberById(addHealthStatRequest.getMemberId());

            HealthStat healthStat = HealthStatMapper.INSTANCE.toHealthStat(addHealthStatRequest);
            healthStat.setMember(member);

            HealthStat addHealthStat = healthStatService.addHealthStat(healthStat);
            ApiResponse<HealthStat> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Add health stat successfully",
                    addHealthStat
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<?>> updateVaccination(
                @PathVariable("id") Integer id,
                @Valid @RequestBody UpdateHealthStatRequest updateHealthStatRequest) {

            HealthStat healthStat = HealthStatMapper.INSTANCE.toHealthStat(updateHealthStatRequest);
            healthStat.setId(id);

            HealthStat updateHealthStat = healthStatService.updateHealthStat(healthStat);
            ApiResponse<HealthStat> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Update vaccination successfully",
                    updateHealthStat
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteHealthStat(@PathVariable("id") Integer id) {
            healthStatService.deleteHealthStat(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<?>> getHealthStatById(@PathVariable("id") Integer id) {
            HealthStat healthStat = healthStatService.getHealthStatById(id);
            ApiResponse<HealthStat> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Get health status successfully",
                    healthStat
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<ApiResponse<List<HealthStat>>> getAllHealthStatsByStatType(
                @RequestParam(defaultValue = "") Integer selectedMemberId,
                @RequestParam(defaultValue = "") String selectedStatType,
                @RequestParam(defaultValue = "") String date) {

            List<HealthStat>  healthStatList = healthStatService.getHealthStatsByStatType(selectedMemberId, selectedStatType, date);

            ApiResponse<List<HealthStat>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Get list vaccination successfully",
                    healthStatList
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/membersSelect")
        public ResponseEntity<ApiResponse<List<Member>>> getAllMembers() {
            User user = authenticationService.getCurrentUser();
            List<Member> membersList = memberService.getAllMembersByUserID(user.getId());

            ApiResponse<List<Member>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Get list member successfully",
                    membersList
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/displayChart")
        public ResponseEntity<ApiResponse<List<HealthStat>>> getHealthStatsToDisplayChart(
                @RequestParam(defaultValue = "") Integer selectedMemberId,
                @RequestParam(defaultValue = "") String selectedStatType,
                @RequestParam(defaultValue = "") String date) {

            List<HealthStat>  healthStatList = healthStatService.getHealthStatsToDisplayChart(selectedMemberId, selectedStatType, date);

            ApiResponse<List<HealthStat>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Get list vaccination successfully",
                    healthStatList
            );

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
