import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchImmobiliComponent } from './search-immobili.component';

describe('SearchImmobiliComponent', () => {
  let component: SearchImmobiliComponent;
  let fixture: ComponentFixture<SearchImmobiliComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchImmobiliComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchImmobiliComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
