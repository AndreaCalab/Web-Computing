import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AstaAnnuncioComponent } from './asta-annuncio.component';

describe('AstaAnnuncioComponent', () => {
  let component: AstaAnnuncioComponent;
  let fixture: ComponentFixture<AstaAnnuncioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AstaAnnuncioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AstaAnnuncioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
