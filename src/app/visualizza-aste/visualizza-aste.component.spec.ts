import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizzaAsteComponent } from './visualizza-aste.component';

describe('VisualizzaAsteComponent', () => {
  let component: VisualizzaAsteComponent;
  let fixture: ComponentFixture<VisualizzaAsteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisualizzaAsteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisualizzaAsteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
