package walter.study.restaurant.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import walter.study.restaurant.domain.Region;
import walter.study.restaurant.domain.RegionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class RegionServiceTest {


    @Mock
    private RegionRepository regionRepository;

    private RegionService regionService;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        regionService = new RegionService(regionRepository);
    }

    @Test
    public void getRegions(){

        List<Region> mockRegions = new ArrayList<>();
        mockRegions.add(Region.builder().name("Seoul").build());

        given(regionRepository.findAll()).willReturn(mockRegions);

        List<Region> regions = regionService.getRegions();

        Region region = regions.get(0);
        assertEquals(region.getName(), "Seoul");
    }

    @Test
    public void addRegion(){
        Region region = regionService.addRegion("Seoul");

        verify(regionRepository).save(any());

        assertEquals(region.getName(), "Seoul");
    }
}